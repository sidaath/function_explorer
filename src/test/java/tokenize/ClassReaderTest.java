package tokenize;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.DirectoryStream;
import java.nio.file.InvalidPathException;
import java.nio.file.NotDirectoryException;


class ClassReaderTest{
	public static void main(String[] args){
		if(args.length < 1){
			System.out.println("No argument provided for test files directory");
			System.exit(1);
		}
		try{
			System.out.println("Running Class Reader Tests");
			Path directoryPath = Paths.get(args[0]);
			DirectoryStream<Path> fileList = Files.newDirectoryStream(directoryPath);
			for(Path file : fileList){
				System.out.println(file.toString());
			}

			fileList.close();
		}catch(InvalidPathException e){
			System.out.println("Argument 1 to ClassReaderTest could not be used to create Path object.");
			System.out.println(e);
			System.exit(1);
		}catch(NotDirectoryException e){
			System.out.println("Directory provided to ClassReader is not valid.");
			System.out.println(e);
			System.exit(1);
		}catch(Exception e){
			System.out.println(e);
		}
		
	}
}
