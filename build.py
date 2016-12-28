import fnmatch
import os
import re
import shutil
import sys

workingPath = os.getcwd()

dirsBlackList = ["build", "\..*", 'backup', 'bin', 'out', 'eclipse', 'gradle', 'out', 'libs']


def findFile(name):
    for f in files:
        if fnmatch.fnmatch(f, "*" + os.path.sep + name):
            return f


def readFile(path):
    return open(path).read()


if not os.path.isfile('build.cfg'):
    f = open('build.cfg', 'w')
    f.write('build=0')
    f.close()

f = open('build.cfg')

config = {}

for l in f:
    split = l.split("=")
    key = split[0]
    value = split[1]
    try:
        value = int(value)
    except Exception as e:
        print e

    config[key] = value

config['build'] += 1

files = []

for f in dirsBlackList:
    print 'ignoring', f

for root, dir, file in os.walk(workingPath):
    for f in file:
        local = root.replace(workingPath + os.path.sep, '')
        add = True
        for b in dirsBlackList:
            if local.startswith('build') or len(re.findall(b, local)) > 0:
                add = False
        if add:
            filePath = root + os.path.sep + f
            files.append(filePath)

# print "Find files =", len(files)

if not os.path.exists('backup'):
    os.mkdir('backup')

print '\n'

mainClass = findFile('OpenTechnology.java')
shutil.shutil.copyfile(mainClass, 'backup' + os.path.sep + 'OpenTechnology.java')

data = readFile(mainClass)
result = re.findall('String.+VERSION.+=.+\"(.*)\"', data)

if result != None:
    data = data.replace(result[0], sys.argv[1] + "_build_" + str(config['build']))
    f = open(mainClass, 'w')
    f.write(data)
    f.close()
    print '1/3'
else:
    print 'Error OpenTechnology.java'
    exit()

mcInfo = findFile('mcmod.info')
shutil.shutil.copyfile(mcInfo, 'backup' + os.path.sep + 'mcmod.info')

data = readFile(mcInfo)

result = re.findall('"version":.+"(.+)",', data)
if result != None:
    data = data.replace(result[0], sys.argv[1] + "_build_" + str(config['build']))
    f = open(mcInfo, 'w')
    f.write(data)
    f.close()
    print '2/3'
else:
    print 'Error mcmod.info'
    exit()

buildGradle = findFile('build.gradle')
shutil.copyfile(buildGradle, 'backup' + os.path.sep + 'build.gradle')

data = readFile(buildGradle)

result = re.findall('version.+=.+"(.+)"', data)
if result != None:
    data = data.replace(result[0], sys.argv[1] + "_build_" + str(config['build']))
    f = open(buildGradle, 'w')
    f.write(data)
    f.close()
    print '3/3'
else:
    print 'Error build.gradle'
    exit()

print 'create commit'

os.system('git add ' + mainClass)
os.system('git add ' + mcInfo)
os.system('git add ' + buildGradle)
os.system('git commit -m "change version" --untracked-files=no')
os.system('git push')
os.system('gradlew build')

pth = (workingPath + '/build/libs/OpenTechnology-' + sys.argv[1] + "_build_" + str(config['build']) + '.jar').replace('/', os.path.sep)

shutil.copyfile(pth, ('C:/Users/Avaja/Desktop/OpenTechnology-' + sys.argv[1] + "_build_" + str(config['build']) + '.jar').replace('/', os.path.sep))

f = open('build.cfg', 'w')
f.write('build=' + str(config['build']))
f.close()
