package com.zwb.wc;

import java.io.IOException;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class wc {
	public static void main(String[] args) throws IOException {
		while (true) {
	        System.out.println("输入：-c   文件名  返回文件的字符数");
	        System.out.println("输入：-w   文件名  返回文件词的数目");
	        System.out.println("输入：-l   文件名  返回文件的行数");
	        System.out.println("输入：-a   文件名  返回文件的代码行 / 空行 / 注释行");
	        System.out.println("输入：-all 文件名  返回文件的字符数 /词数 /行数/注释行 /空行 /代码行 ");
	        System.out.print("请输入命令：");
			Scanner s = new Scanner(System.in);
	        String m =s.nextLine();
	        String arr[]=m.split("\\s");
	     
			count.command(arr[1], arr[0]);	
			
		}
	}
}

 class searchFile {
	
	public static void foundFilePath(String path, String fileName) throws IOException {

        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (null == files || files.length == 0) {
                System.out.println("文件夹是空的!");
                return;
            } else {
                for (File file2 : files) {
                    if (file2.isDirectory()) {                    
                        foundFilePath(file2.getAbsolutePath(), fileName);
                    } else if (file2.getName().contains(fileName)) {
                    	System.out.println("文件:" + file2.getAbsolutePath());
                    	count.command(file2.getAbsolutePath(), "-all");
					}
                }
            }
        } else {
            System.out.println("文件不存在!");
        }
    }
}


class count{
	static int cntCode = 0, cntNode = 0, cntSpace = 0;
    static boolean flagNode = false;
    public static void command(String path, String type) throws IOException {
    	BufferedReader br = null;
        int countWord = 0;
        int countChar = 0;
        int countLine = 0;
        String str = "";
        String strCount = "";
        br = new BufferedReader(new FileReader(path));
        while((str = br.readLine()) != null ){
        	// 计算行数
        	countLine++;
        	// 计算特殊行的数目
        	if (type.equals("-a")||type.equals("-all")) {
        		pattern(str);
        	}
        	
        	str = str + " ";
            strCount += str;
        }    
        for(int i=0;i<strCount.split(" ").length;i++){
        	if (type.equals("-c")||type.equals("-all")) {
            	countChar += strCount.split(" ")[i].length();
			}
        	if (type.equals("-w")||type.equals("-all")) { 
        		String s = strCount.split(" ")[i].replaceAll("[\\p{P}+~$`^=|<>～｀＄＾＋＝｜＜＞￥×]" , "");
        		boolean word = !s.equals("");
                if(word) {
                	countWord++;
    			}
            }
        }
        
        if (type.equals("-w")||type.equals("-all")) {
        	System.out.println("单词数：" + countWord);
		}
        if (type.equals("-c")||type.equals("-all")) {
        	System.out.println("字符数：" + countChar);
		}
        if (type.equals("-l")||type.equals("-all")) {
        	System.out.println("行数：" + countLine);
		}
        if (type.equals("-a")||type.equals("-all")) {
	        System.out.println("注释行： " + cntNode);
	        System.out.println("空行： " + cntSpace);
	        System.out.println("代码行： " + cntCode);
        }
        System.out.println();
        br.close();
        cntNode = 0;
        cntSpace = 0;
        cntCode = 0;
        flagNode = false;
    }
    
    // 计算特殊行的数量
    public static void pattern(String line) {
    	String regxNodeBegin = "\\s*/\\*.*";
        String regxNodeEnd = ".*\\*/\\s*";
        String regx = "//.*";
        String regxSpace = "\\s*";
        if(line.matches(regxNodeBegin) && line.matches(regxNodeEnd)){
            ++cntNode;
            return ;
        }
        if(line.matches(regxNodeBegin)){
            ++cntNode;
            flagNode = true;
        } else if(line.matches(regxNodeEnd)){
            ++cntNode;
            flagNode = false;
        } else if(line.matches(regxSpace))
            ++cntSpace;
        else if(line.matches(regx))
            ++cntNode;
        else if(flagNode)
            ++cntNode;
        else ++cntCode;
    }
}
