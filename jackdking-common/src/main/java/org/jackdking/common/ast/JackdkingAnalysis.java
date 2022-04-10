package org.jackdking.common.ast;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.expr.Name;
import org.eclipse.jdt.core.dom.*;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName JackdkingAnalysis
 * @Description TODO
 * @Author jackdking
 * @Date 29/03/2022 2:32 下午
 * @Version 2.0
 **/
public class JackdkingAnalysis {

    private static final String CLASS_PATH = ClassLoader.getSystemClassLoader().getResource("").getPath();
    private static final File PROJECT_FILE = new File(CLASS_PATH).getParentFile().getParentFile();
    private static final File SRC_MAIN_JAVA_FILE = new File(PROJECT_FILE, "/src/main/java");
    private static final File SRC_MAIN_CLASS_FILE = new File(PROJECT_FILE, "/target/classes");
    private static final String FILE_NAME = JackdkingAnalysis.class.getName().replace('.', '/') + ".java";
    private static final String CLASS_FILE_NAME = JackdkingAnalysis.class.getName().replace('.', '/') + ".class";
    private static final File FILE = new File(SRC_MAIN_JAVA_FILE, FILE_NAME);
    private static final File CLASS_FILE = new File(SRC_MAIN_CLASS_FILE, CLASS_FILE_NAME);

    public static void main(String[] args) throws Exception {
        System.out.println(FILE.getAbsoluteFile().toPath());
        byte[] bytes = Files.readAllBytes(FILE.getAbsoluteFile().toPath());
        String s = new String(bytes, StandardCharsets.UTF_8);

        int COUNT = 1_000;

        long time1 = System.currentTimeMillis();

        // javaparser
        for (int i = 0; i < COUNT; i++) {
            CompilationUnit cu = StaticJavaParser.parse(s);
            cu.findAll(ImportDeclaration.class).forEach(a -> {
                Name name = a.getName();
            });
        }

        long time2 = System.currentTimeMillis();

        // eclipse-astparser

        org.eclipse.jdt.core.dom.CompilationUnit unit = null;
        ASTParser parser = null;
        for (int i = 0; i < COUNT; i++) {
            parser = ASTParser.newParser(AST.JLS8);
            parser.setSource(s.toCharArray());
            parser.setKind(ASTParser.K_COMPILATION_UNIT);
            unit = (org.eclipse.jdt.core.dom.CompilationUnit) parser.createAST(null);

            unit.setProperty("hello", "java");

            List<org.eclipse.jdt.core.dom.ImportDeclaration> imports = unit.imports();
            imports.forEach(a -> {
                org.eclipse.jdt.core.dom.Name name = a.getName();
            });
        }

        TypeDeclaration typeDec = (TypeDeclaration) unit.types().get(0);
        FieldDeclaration fieldDec[] = typeDec.getFields();
        System.out.println("Fields:");
        for(FieldDeclaration field: fieldDec)
        {
            System.out.println("Field fragment:"+field.fragments());
            System.out.println("Field type:"+field.getType());
        }
        FieldDeclaration fieldDeclaration = unit.getAST().newFieldDeclaration(unit.getAST().newVariableDeclarationFragment());
        typeDec.bodyDeclarations().add(0, createLiceseInLineField(unit.getAST()));

        System.out.println(unit.toString());

        FileOutputStream fo = new FileOutputStream(CLASS_FILE);
        fo.write(unit.toString().getBytes(StandardCharsets.UTF_8));
        fo.flush();

        long time3 = System.currentTimeMillis();

        System.out.println(time2 - time1); // 6146
        System.out.println(time3 - time2); // 1467
    }

    private static FieldDeclaration createLiceseInLineField(AST ast) {
        VariableDeclarationFragment vdf = ast.newVariableDeclarationFragment();
        vdf.setName(ast.newSimpleName("log"));
        StringLiteral sl = ast.newStringLiteral();
        sl.setLiteralValue("hello java ast");
        vdf.setInitializer(sl);
        FieldDeclaration fd = ast.newFieldDeclaration(vdf);
        fd.modifiers().addAll(ast.newModifiers(Modifier.PUBLIC | Modifier.STATIC | Modifier.FINAL));
        fd.setType(ast.newSimpleType(ast.newSimpleName("JackdkingAnalysis")));

        return fd;
    }
}
