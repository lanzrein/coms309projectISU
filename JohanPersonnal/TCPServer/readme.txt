Tentative of a TCP server for message exhange. 
With lots of help from : https://github.com/CatalinPrata/funcodetuts

-> server seems okay can establish a connection to a phone. 
-> client side is functionnal for 1 user
-> will be usefull when trying to set up a server for our app

-> In order to access network with a method that is NOT on the main class you have to either create a class as a sub class of 
AsyncTask or (this trick looks very dirty but works) put the part that access network in a thread block e.g.
Thread td = new Thread(new Runnable(){
	@override
	public void run(){
		//the code that access network
	}
});
td.run();
