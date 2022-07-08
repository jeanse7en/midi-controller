import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CreateJavaFolder {
    @Test
    public void generateFolder() {
        for (int i = 37; i <= 42; i ++) {
            File theDir = new File("/home/ubuntu/Documents/hey-rin-store/images/" + "heyrin_" + i);
            if (!theDir.exists()){
                theDir.mkdirs();
            }
        }
    }

    @Test
    public void copyFileToFolder() throws IOException {
        for (int i = 43; i <= 47; i ++) {
            File source = new File(String.format("/home/ubuntu/Documents/hey-rin-store/raw_picture/iloveimg-resized/%s.jpg", "heyrin_" + i, "heyrin_" + i));
            File dest = new File(String.format("/home/ubuntu/Documents/hey-rin-store/%s/%s.jpg", "heyrin_" + i, "heyrin_" + i));
            FileUtils.copyFile(source, dest);
        }

    }

    @Test
    public void copyFileToFolder2() throws IOException {
        File resizeFolder = new File("/home/ubuntu/Documents/hey-rin-store/raw_picture/iloveimg-resized");
        for (final File fileEntry : resizeFolder.listFiles()) {
            if (fileEntry.isDirectory()) {
            } else {
                String fileName = fileEntry.getName();
                String folderName = fileName.substring(0, fileName.lastIndexOf("_"));
                File dest = new File(String.format("/home/ubuntu/Documents/hey-rin-store/images/%s/%s", folderName, fileName));
                FileUtils.copyFile(fileEntry, dest);
            }
        }

    }

    @Test
    public void getHtmlFileProduct() throws IOException {
        String defaultText = String.format("<div class=\"col-4\">\n" +
                "                <img src=\"../images/heyrin_<folder>/heyrin_<folder>.jpg\">\n" +
                "                <a href=\"../images/heyrin_<folder>/heyrin_<folder>.jpg\"><h4>heyrin_<folder></h4></a>\n" +
                "                <p>$50.00</p>\n" +
                "            </div>\n");
        StringBuilder output = new StringBuilder();
        for (int i = 1; i < 40; i ++) {
            output.append(defaultText.replace("<folder>", i + ""));
        }
        System.out.println(output);
    }

}