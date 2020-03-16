import java.io.*;
import java.net.*;
import java.util.*;

public final class TracerouteAnalysis {
	public static void main(String argv[]) throws IOException { 
		// Open the file
		FileInputStream fstream = new FileInputStream("sampletcpdump.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

		String strLine;
		String ip  = "ip";
		String tcp  = "tcp";
		String icmp  = "icmp";
		String dot  = ".";
		Vector<String>  v1 = new Vector<String>(10);
		Vector<String>  v2 = new Vector<String>(10);
		
		//Read File Line By Line
		while ((strLine = br.readLine()) != null)   {
		  // Print the content on the console


			if ( strLine.toLowerCase().indexOf(ip.toLowerCase()) != -1 ) {
				if ( strLine.toLowerCase().indexOf(dot.toLowerCase()) != -1 ) {
					if ( strLine.toLowerCase().indexOf(tcp.toLowerCase()) != -1 ) {
						//System.out.println (strLine);
						v1.add(strLine);
					}
				} 
			} 
			if( strLine.toLowerCase().indexOf(icmp.toLowerCase()) != -1 ) {
				v2.add(strLine);
				strLine = br.readLine();
				v2.add(strLine);
				strLine = br.readLine();
				v2.add(strLine);
						
				//System.out.println (strLine);
			}
			
		}
		//Close the input stream
		br.close();
		for( int i=0; i<v1.size();i++ ){
			//System.out.println (v1.get(i));
		}
		for( int i=0; i<v2.size();i++ ){
			//System.out.println (v2.get(i));
		}
		String[][] v11= new String[v1.size()][3]; 
		String[][] v22= new String[v2.size()][3];
		for( int i=0; i<v1.size();i++ ){
			String str = v1.get(i);
			int index = str.indexOf(' ');
			String ttl = str.substring(str.indexOf("l") + 2, str.indexOf("i")-2);
			String id = str.substring(str.indexOf("d") + 2, str.indexOf("f")-3);
			String time = str.substring(0, index);
			v11[i][0] = time;
			v11[i][1] = id;
			v11[i][2] = ttl;
			//System.out.println (ttl);
			//System.out.println (time);
			//System.out.println (id);
		}
		for( int i=0; i<v1.size();i++ ){		
			//System.out.print (v11[i][0] +", ");
			//System.out.print (v11[i][1]+", ");
			//System.out.println (v11[i][2]);
		}
		int counter = 0;
		for( int i=0; i<v2.size();i++ ){
			String str = v2.get(i);
			int index = str.indexOf(' ');
			String time = str.substring(0, index);
			//System.out.println (time);
			v22[counter][0] = time;
			i++;
			if(i== v2.size())
				break;
			str = v2.get(i);
			index = str.indexOf(' ');
			String IP = str.substring(4, str.indexOf(">"));
			//System.out.println (IP);
			//System.out.println (str);
			v22[counter][2] = IP;
			i++;
			if(i== v2.size())
				break;
			str = v2.get(i);
			String id = str.substring(str.indexOf("d") + 2, str.indexOf("f")-3);
			v22[counter][1] = id;
			counter++;
			//System.out.println (id);
		}
		for( int i=0; i<counter ;i++ ){
			//System.out.print (v22[i][0] +", ");
			//System.out.print (v22[i][1]+", ");
			//System.out.println (v22[i][2]);
		}
		for( int i=0; i<v1.size();i++ ){

			int id1 = Integer.parseInt(v11[i][1]);
			for( int j=0; j<counter ;j++ ){
				int id2 =  Integer.parseInt(v22[j][1]);
				if(id1 == id2){
					if (i%3==0){
						System.out.println ();
						System.out.println ("TTL " + v11[i][2]);
						System.out.println(v22[j][2]);
					}
					double time1 = Double.parseDouble(v11[i][0]);
					double time2 = Double.parseDouble(v22[j][0]);
					double ftime = (time2-time1)*1000;
					System.out.printf("%.3f", ftime);
					System.out.println(" ms");
				}
			}
			
		}
	}
}
