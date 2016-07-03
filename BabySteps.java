
//BabySteps * * * * * * * * * * * * * * * *     
	
	public void BabySteps(int Minuten, boolean Baby ){
		
		setMinute(Minuten);
		setBabyBoolean(Baby);
		
	}
	
	public void setMinute(int Minuten){
		this.Minuten = Minuten;
		
	}
	
	public int getMinute(){
		return Minuten;
	}
	
	
	public void setBabyBoolean(boolean Baby){
		this.Baby = Baby;
		
	}
	
	public boolean getBabyBoolean(){
		return Baby;
	}
	
	public void setInterrupt(boolean Interrupt){
		this.Interrupt = Interrupt;
	}
	
	public boolean getInterrupt(){
		return Interrupt;
	}
	

	public void StartTimer(Status status ){

		int Minuten = getMinute();
		long Vergleich = ConvertSeconds(Minuten);
		 Stoppuhrstarte(status, Vergleich);
	}
	
	public long ConvertSeconds(int Minuten){
	long Vergleich  = Minuten/2;
	return Vergleich;
	}
	
	void Stoppuhrstarte(Status status, Long Vergleich)
	{ 
		
		seconds = 0;
		try {
		while(seconds<=Vergleich){
			
		
			
			 Thread.sleep(1000); seconds++;}
		
		if(getInterrupt()==false){
		if(status==Status.Green||status==Status.BabyGreen){;setStatus(Status.BabyRed);}
		if(status==Status.Red||status==Status.BabyRed){setStatus(Status.BabyGreen);}}
		
		if(getInterrupt()==true){
		if(status==Status.Green||status==Status.BabyGreen){;setStatus(Status.Red);}
		if(status==Status.Red||status==Status.BabyRed){setStatus(Status.Green);}}
		
		
		
		}
		catch (InterruptedException e) {
            e.printStackTrace();
        }
		
	}
	
	
