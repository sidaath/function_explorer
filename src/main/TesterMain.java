import java.io.*;
import java.util.Map;
import java.util.List;
class TestMain{

	public static void main(String[] args){
		try{
			System.out.println("test file = "+args[0]);
			File file = new File(args[0]);
			Map<String, List<String>> result = Tokenizer.tokenizeFile(file);
			for(Map.Entry<String, List<String>> entry : result.entrySet()){
				System.out.println("class = "+entry.getKey());
				List<String> vals = entry.getValue();
				for(String val : vals){
					System.out.println("token = "+val);
				}
			}
		}catch(Exception e){
			System.out.println(e);
		}
	}
}
