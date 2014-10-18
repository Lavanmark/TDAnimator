package tdAnimator;

import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Animation implements Serializable{

	/**
	 * auto generated
	 */
	private static final long serialVersionUID = 5239834985542385162L;
	
	private int updateTime;
	private BufferedImage sheet;
	private BufferedImage[] steps;
	private int startStep;
	private int numOfSteps;
	private int curStep;
	private int lastDraw;
	
	private SheetGrabber sg1;
	
	
	public Animation(int time, BufferedImage sheetImg, int numberOfSteps, int stepToStart){
		updateTime = time;
		sheet = sheetImg;
		numOfSteps = numberOfSteps;
		startStep = stepToStart;
		curStep = startStep;
		steps = new BufferedImage[numOfSteps];
		sg1 = new SheetGrabber(sheet);
		lastDraw = 0;
	}
	
	//set image at first available spot
	public void setImg(int x, int y, int width, int height){
		int loc = 0;
		for(int i = 0; i < numOfSteps; i++){
			if(steps[i] == null){
				loc = i;
			}
		}
		steps[loc] = sg1.grabSprite(x, y, width, height);
	}
	//set image at index given
	public void setImg(int x, int y, int width, int height, int index){
		steps[index] = sg1.grabSprite(x, y, width, height);
	}
	
	public BufferedImage getStepImg(){
		return steps[curStep-1];
	}
	
	public BufferedImage getStepImg(int index){
		return steps[index-1];
	}
	public void nextStep(){
		curStep++;
		if(curStep > numOfSteps){
			curStep = startStep;
		}
	}
	
	public int getLast(){
		return lastDraw;
	}
	public void setLast(int lastTick){
		lastDraw = lastTick;
	}
	public void setStep(int step){
		curStep = step;
	}
	public void setUpdateTime(int time){
		updateTime = time;
	}
	public int getUpdateTime(){
		return updateTime;
	}
	
	class SheetGrabber implements Serializable{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public BufferedImage sheet;
			
		public SheetGrabber(BufferedImage ss){
			this.sheet = ss;
		}
		
		public BufferedImage grabSprite(int x, int y, int width, int height){
			BufferedImage sprite = sheet.getSubimage(x, y, width, height);
			return sprite;
		}
	}
}
