import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

//Auto-Document Formatter for Java Code
public class ADF {
    private List<Node> sourceNodes;
    public ADF(){
        sourceNodes  = new ArrayList<>();
    }

    private String getFileContents(String param_file_path){
        try{
            File file = new File(param_file_path);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String current_line;
            StringBuilder stringBuilder = new StringBuilder();
            while((current_line = bufferedReader.readLine()) != null){
                stringBuilder.append(current_line);
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            logg_err("Error reading file");
            throw new RuntimeException(e);
        }
    }
    public void parseSourceFile(String param_path){
        CompilationUnit parsed = StaticJavaParser.parse(getFileContents(param_path));
        sourceNodes = parsed.getChildNodes();
        logg("Parsed Source File");
    }

    /*
        reads and returns nodes of java source file
    */
    public List<Node> getNodes(){
        if (sourceNodes.isEmpty()){
            logg_err("No Source Nodes");
        }
       return sourceNodes;
    }

    private void logg(String message){
        System.out.println("ADF : "+message);
    }

    private void logg_err(String message){

        System.err.println("ADF-err : "+message);
    }
}
