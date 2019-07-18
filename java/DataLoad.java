import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class DataLoad {
    ArrayList<String> list = new ArrayList<>();
    BufferedReader br = null;
    HashMap<Character, ArrayList<String>> map = new HashMap<>();
    public DataLoad(){
        System.out.println("test1");
}

    public void load(){
        int num = 0;
        try{
            br = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\dongha\\Desktop\\kkutu\\kkutu_ko.csv"),"EUC-KR"));

            String line = "";
            System.out.println(line);
            while((line = br.readLine()) != null){
                //CSV 1행 ->  line
                num++;
                System.out.println(line);
                list.add(line);
            }


        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                System.out.println(num);
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void mapping(){
        for(String word:list){
            if(!map.containsKey(word.charAt(0))){
                ArrayList<String> tmp = new ArrayList<>();
                tmp.add(word);
                map.put(word.charAt(0), tmp);
            }else{
                map.get(word.charAt(0)).add(word);
            }
        }
    }

    public void dataSort(){

        map.forEach((k, arrayList) -> Collections.sort(arrayList, new Comparator<String>(){
            @Override
            public int compare(String str1, String str2){
                return str2.length() - str1.length();
            }
        }));
    }

    public void display(){
        map.forEach((k, arrayList) -> System.out.println(arrayList.get(0)));
    }


}
