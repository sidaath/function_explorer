package tokenize;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.DirectoryStream;
import java.io.*;
import java.nio.file.InvalidPathException;
import java.nio.file.NotDirectoryException;
import java.util.List;


class ClassReaderTest{
	public static void main(String[] args){
		if(args.length < 2){
			System.out.println("Incorrect number of arguments provided.");
			System.out.println("Arg 1 : Path to directory of test source files");
			System.out.println("Arg 2 : Path to directory of corresponding results to check against");
			System.exit(1);
		}
		try (
			DirectoryStream<Path> javaFileListPaths = Files.newDirectoryStream(Paths.get(args[0]));
		){
			System.out.println("Running Class Reader Tests");
			for(Path path : javaFileListPaths){
				File file = path.toFile();
				
				InputStream inputStream = new FileInputStream(file);
				Reader rReader = new InputStreamReader(inputStream);
				BufferedReader bReader0 = new BufferedReader(rReader);
				while((char)bReader0.read() != '{'){
				}

				List<String> tokensFromClass = ClassReader.processClass(bReader0);

				String javaFileName = path.getFileName().toString().split("\\.")[0];

				String resultFilePath = args[1]+"/"+javaFileName+".result";

				File resultFile = new File(resultFilePath);
				inputStream = new FileInputStream(resultFile);
				Reader resReader = new InputStreamReader(inputStream);
				BufferedReader bReader = new BufferedReader(resReader);

				String[] compare = bReader.readLine().split(";");
				
				String compareToken = "";
				String resultToken = "";
				boolean pass = true;
				for(int i = 0; i < compare.length; i++){
					compareToken = compare[i];
					resultToken = tokensFromClass.get(i);

					if(!resultToken.equals(compareToken)){
						pass = false;
					}
				}
				if(!pass){
					System.out.println("Failed @ File : "+path.toString());
				}else{
					System.out.println("Passed @ File : "+path.toString());
				}
			}

		}catch(InvalidPathException e){
			System.out.println("Argument 1 to ClassReaderTest could not be used to create Path object.");
			System.out.println(e);
			System.exit(1);
		}catch(NotDirectoryException e){
			System.out.println("Directory provided to ClassReader is not valid.");
			System.out.println(e);
			System.exit(1);
		}catch (IOException e){
			System.out.println("IO Exception");
			System.out.println(e);
		}
		
	}
}
