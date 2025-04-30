class TokenizerTest{
	public static void main(Strin[] args){
		System.out.println("Test file : "+args[0]);
		File file = new File(args[0]);
		Map<String, List<String>> tokenMap = Tokenizer.tokenizeFile(file);

	}
}
