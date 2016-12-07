package OpenTechnology.render.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class LDAConnectorModel extends ModelBase {
  //fields
    ModelRenderer Shape1;
    ModelRenderer Shape2;
    ModelRenderer Shape3;
  
  public LDAConnectorModel()
  {
    textureWidth = 64;
    textureHeight = 32;
    
      Shape1 = new ModelRenderer(this, 40, 0);
      Shape1.addBox(0F, 0F, 0F, 6, 16, 6);
      Shape1.setRotationPoint(0F, 0F, 0F);
      Shape1.setTextureSize(64, 32);
      Shape1.mirror = true;
      setRotation(Shape1, 0F, 0F, 0F);
      Shape2 = new ModelRenderer(this, 0, 22);
      Shape2.addBox(0F, 0F, 0F, 10, 6, 4);
      Shape2.setRotationPoint(-2F, 10F, -4F);
      Shape2.setTextureSize(64, 32);
      Shape2.mirror = true;
      setRotation(Shape2, 0F, 0F, 0F);
      Shape3 = new ModelRenderer(this, 0, 0);
      Shape3.addBox(0F, 0F, 0F, 10, 2, 5);
      Shape3.setRotationPoint(-2F, 10F, -4F);
      Shape3.setTextureSize(64, 32);
      Shape3.mirror = true;
      setRotation(Shape3, 0.4886922F, 0F, 0F);
  }
  
  public void render()
  {
    Shape1.render(0.0625F);
    Shape2.render(0.0625F);
    Shape3.render(0.0625F);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }

  public void addRotation(ModelRenderer model, float x, float y, float z){
    model.rotateAngleX += x;
    model.rotateAngleY += y;
    model.rotateAngleZ += z;
  }

  public void addRotationAll(float x, float y, float z){
    addRotation(Shape1, x, y, z);
    addRotation(Shape2, x, y, z);
    addRotation(Shape3, x, y, z);
  }

  public void setRotationAll(float x, float y, float z){
    setRotation(Shape1, x, y, z);
    setRotation(Shape1, x, y, z);
  }
}
