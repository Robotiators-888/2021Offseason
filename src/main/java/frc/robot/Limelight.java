package frc.robot;

public class Limelight {
  NetworkTable table;

  public limelight(){
    table = inputTable.getDefault().getTable("limelight");
  } 

  /**
  * Whether the limelight has any valid targets
  * @return boolean ture if target is found false if not
  */
  public boolean getTv(){
    if(table.getEntry("tv").getDouble(0) == 1){
      return true;
    }else{
      return false;
    }
  }

  /**
  * Horizontal Offset From Crosshair To Target
  * @return double of offset of target y-value
  */
  public double getTx(){
    return table.getEntry("tx").getDouble(0);
  }

  /**
  * 	Vertical Offset From Crosshair To Target
  * @return double of offset of target y-value
  */
  public double getTy(){
    return table.getEntry("ty").getDouble(0);   
  }

  /**
  * Target Area
  * @return double of target area (0% of image to 100% of image)
  */
  public double getTa(){
    return table.getEntry("ta").getDouble(0);   
  }

  /**
  * Skew or rotation
  * @return double of skew/rotation (-90 degrees to 0 degrees)
  */
  public double getTs(){
    return table.getEntry("ts").getDouble(0);
  }

  /**
  * The pipeline’s latency contribution
  * @return double of pipeline latency
  */
  public double getTl(){
  
    return table.getEntry("tl").getDouble(0); 
         

  }

}