package xyz.xingfeng.tieba;

import java.util.ArrayList;

public class HtmlContent {
    private String html;
    public HtmlContent(String html){
        this.html = html;
    }

    /**
     * 根据标签查找
     */
    public ArrayList<Content> getByHtml(String htmlName){
        ArrayList<Content> label = new ArrayList<>();
        //为了不污染导入的内容，新建一个储存内容的变量
        String content = html;
        int code = 0;
        while (true) {
            //寻找带有目标内容的标签
            int startHtml = indexOf(content,"<" + htmlName,code);
            System.out.println(code + "st:" + startHtml);
            //寻找结尾标签
            int endHtml = indexOf(content,"</" + htmlName + ">",code);
            System.out.println(code + "end:" + endHtml);
            if (startHtml < 0){
                break;
            }
            if (endHtml < 0){
                break;
            }
            if (endHtml<startHtml){
                content = content.replace(content.substring(endHtml,endHtml+10),"");
                continue;
            }

            //查看两个标签中是否存在另一个开始标签
            if (content.substring(startHtml+1+htmlName.length(),endHtml).contains("<" + htmlName)){
                //存在
                int codes = code+1;
                //寻找下一个带有目标内容的标签
                int nextStartHtml;
                //寻找下一个结尾标签
                int nextEndHtml ;
                do {
                    nextStartHtml = indexOf(content,"<" + htmlName,codes);
                    nextEndHtml = indexOf(content,"</" + htmlName + ">",codes);
                    if (nextStartHtml < 0){
                        break;
                    }
                    if (nextEndHtml < 0){
                        content = content.replace(content.substring(nextStartHtml,nextStartHtml+10),"");
                        codes++;
                        break;
                    }
                    codes++;
                }while (content.substring(nextStartHtml+1+htmlName.length(),nextEndHtml).contains("<" + htmlName));
                if (nextEndHtml > 0 && nextStartHtml > 0){
                    label.add(new Content(content.substring(startHtml,nextEndHtml+3+htmlName.length())));
                }
                code++;
                continue;
            }
            //不存在
            label.add(new Content(content.substring(startHtml,endHtml+3+htmlName.length())));
            code++;
        }
        return label;
    }

    private int indexOf(String s, String str, int fromIndex){
        int strLength = str.length();
        int i = -1;
        i = s.indexOf(str);
        String next = s.substring(i+str.length());
        for (int x = 0; x < fromIndex; x++){
            int code = next.indexOf(str);
            if (code < 0){
                i = -1;
                break;
            }
            i += code + strLength;
            next = next.substring(code + strLength);
        }
        return i;
    }

    public void getById(){

    }

    public void getByClass(){

    }
    public class Content{
        String s;
        String htmlLabel;
        ArrayList<String> className = new ArrayList<>();
        String id;
        String value;
        private String text;
        //控件类，记录控件的信息
        public Content(String contentHtml){
            this.s = contentHtml;
            if (contentHtml.indexOf(" ") < (contentHtml.indexOf(">"))){
                this.htmlLabel = contentHtml.substring(contentHtml.indexOf("<") + 1, contentHtml.indexOf(" "));
            }else {
                this.htmlLabel = contentHtml.substring(contentHtml.indexOf("<") + 1, contentHtml.indexOf(">"));
            }
            this.text = contentHtml.substring(contentHtml.indexOf(">")+1,contentHtml.lastIndexOf("<")).trim();
            this.value = contentHtml.substring(contentHtml.indexOf("<"),contentHtml.indexOf(">")+1);
        }

        public String getText() {
            return text;
        }

        private int indexOf(String s, String str, int fromIndex){
            int strLength = str.length();
            int i = -1;
            i = s.indexOf(str);
            String next = s.substring(i+str.length());
            for (int x = 0; x < fromIndex; x++){
                int code = next.indexOf(str);
                if (code < 0){
                    i = -1;
                    break;
                }
                i += code + strLength;
                next = next.substring(code + strLength);
            }
            return i;
        }
    }
}
