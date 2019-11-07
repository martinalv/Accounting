package Driver;

import Adaptor.AddAccount;
import Adaptor.BuildAccounting;
import Adaptor.CalculateAccounting;
import Adaptor.CreateAccounting;
import Adaptor.LoadSavedData;
import Adaptor.Print;

public class driver {

	public static void main(String[] args){
		// TODO Auto-generated method stub
		
		CreateAccounting builder = new BuildAccounting();
		LoadSavedData loader = new BuildAccounting();
		AddAccount accountAdder = new BuildAccounting();
		CalculateAccounting calculator = new BuildAccounting();
		Print printer = new BuildAccounting();
		
		builder.construct();
		
		loader.loadSavedData(args[0]);
		
		for(int i = 1; i < args.length; i++){
			accountAdder.addAcct(args[i]);
		}
		
		calculator.calculate();
		
		printer.setPrintDirectory(String.valueOf(System.getProperty("user.dir")) + "/");
		printer.printAll();
		
		System.out.println("DONE");

	}

}
