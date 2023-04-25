package app.T01_JavaParse;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.expr.SimpleName;

import java.io.File;

public class Parser {

    /**
     * 解析单个Java文件
     *
     * @param cu 编译单元
     */
    public static void parseOneFile(CompilationUnit cu) {
        // 类型声明
        NodeList<TypeDeclaration<?>> types = cu.getTypes();
        for (TypeDeclaration<?> type : types) {
            System.out.println("## " + type.getName());
            // 成员,貌似getChildNodes方法也可行
            NodeList<BodyDeclaration<?>> members = type.getMembers();
            members.forEach(Parser::processNode);
        }
    }

    /**
     * 处理类型,方法,成员
     *
     * @param node
     */
    public static void processNode(Node node) {
        if (node instanceof TypeDeclaration) {
            // 类型声明
            // do something with this type declaration

        } else if (node instanceof MethodDeclaration) {
            // 方法声明
            // do something with this method declaration
            String methodName = ((MethodDeclaration) node).getName().getIdentifier();
            System.out.println("方法喵: " + methodName);
            Comment comment = node.getComment().orElse(null);
            System.out.println("方法的注释: " + comment);

        } else if (node instanceof FieldDeclaration) {
            // 成员变量声明
            // do something with this field declaration
            // 注释
            Comment comment = node.getComment().orElse(null);

            // 变量
            NodeList<VariableDeclarator> variables = ((FieldDeclaration) node).getVariables();
            SimpleName fieldName = variables.get(0).getName();
            if (comment != null) {
                String content = comment.getContent();
                //con = content.replace("*", "").trim();
                System.out.println("成员变量注释喵:" + content);

            }
            System.out.print("\t");
            System.out.print("成员变量喵:" + fieldName);
            System.out.println();

        }
    }

    public static void main(String[] args) throws Exception {
        String classPath = "F:\\codeworkbase\\java\\github.com\\PlagueCat-Miao\\MiaoMiaoSpringBoot\\src\\main\\java\\com\\plague\\learn\\T02_Gson\\UseGson.java";
        JavaParser aa = new JavaParser();

        ParseResult<CompilationUnit> res = aa.parse(new File(classPath));
        CompilationUnit cu = res.getResult().get();
        parseOneFile(cu);

    }


}
