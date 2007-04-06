package graphe3D.universe;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.PhysicalBody;
import javax.media.j3d.PhysicalEnvironment;
import javax.media.j3d.View;
import javax.media.j3d.ViewPlatform;

/*
 * create : GView():GView
 * create : GView(canvas:Canvas3D):GView)
 */
public class GView extends BranchGroup{
	
	protected PhysicalBody physBody=null;
	protected PhysicalEnvironment physEnv=null;
	protected ViewPlatform viewPlatform=null;
	protected View view=null;
	protected Canvas3D canvas3d=null;
	  
	public GView(){
		physBody=new PhysicalBody();
		physEnv=new PhysicalEnvironment();
		
	}
	
	public GView(Canvas3D canvas){
		canvas3d=canvas;
	}

}
