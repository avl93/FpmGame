package ru.kubsu.fpm.game;

class AiThread extends Thread{
	BotAI ai;
	FieldInfo f;
	int i;
	
	long lastUpd;
	int exceptionsCount;	
	
	public AiThread(BotAI ai, int i) {
		this.ai=ai;
		this.i=i;
		this.f=null;
		exceptionsCount=0;
		lastUpd = System.currentTimeMillis();
	}

	public void upd(FieldInfo f){
		this.f=f;
	}

	
	@Override
	public void run() {
		while(!isInterrupted()){
			while (f==null){
				try {
					synchronized (this) {
						this.wait(10);	
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			try {
				lastUpd = System.currentTimeMillis();
				ai.upd(f, i);		
				f=null;
			} catch (Exception e){
				System.out.println("Bot " + Integer.toString(i) + " - throwed exception!");
				exceptionsCount++;
				e.printStackTrace();
			}
		}
	}

}
