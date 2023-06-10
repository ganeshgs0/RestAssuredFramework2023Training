package api.utilities;

import org.testng.annotations.DataProvider;

public class DataProviders {
    
	@DataProvider(name="Data")
	public String [][] getAllData() throws Exception {
		String path=System.getProperty("user.dir")+"//testdata//Userdata.xlsx";
		XLUtility xl=new XLUtility(path);
		int rownum=xl.getRowcount("Sheet1");
		int colnum=xl.getCellcount("Sheet1", 1);
		String [][] apidata=new String [rownum][colnum];
		for(int i=1;i<=rownum;i++) {
			for(int j=0;j<colnum;j++) {
				apidata[i-1][j]=xl.getCelldata("Sheet1", i, j);
			}
		}
		return apidata;		
	}
	
	@DataProvider(name="Usernames")
	public String[] getUsernames() throws Exception {
		String path=System.getProperty("user.dir")+"//testdata//Userdata.xlsx";
		XLUtility xl=new XLUtility(path);
		int rownum=xl.getRowcount("Sheet1");
		String [] apidata=new String[rownum];
		for(int i=1;i<=rownum;i++) {
			apidata[i-1]=xl.getCelldata("Sheet1", i, 1);
		}
		return apidata;
	}
}
