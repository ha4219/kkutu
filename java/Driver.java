import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Driver {
    private WebDriver driver;
    private WebElement currentElement;
    private List<WebElement> currentElements;
    private static final String WEB_DRIVER_ID = "webdriver.chrome.driver";
    private static final String WEB_DRIVER_PATH = "C:\\Chrome_Driver\\chromedriver.exe";
    Scanner scanner = new Scanner(System.in);
    private String URL;
    private String ID;
    DataLoad dataLoad = new DataLoad();
    public Driver(String url) {
        super();

        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
        driver = new ChromeDriver();
        this.URL = url;
    }

    public void start(){
        try {
            driver.get(this.URL);
            dataLoad.load();
            dataLoad.mapping();
            dataLoad.dataSort();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean entryGame(){
        String title = "";
        driver.findElement(By.xpath("//*[@id=\"game-start\"]")).click();
        try {
            System.out.println("아이디를 입력해주세요: ");
            ID = scanner.nextLine();
            System.out.println("방 제목을 입력해주세요: ");
            title = scanner.nextLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<WebElement> wes = driver.findElements(By.className("rooms-item"));
        for(WebElement we : wes){
            currentElement = we.findElement(By.className("rooms-title"));
            if(currentElement.getText().equals(title)){
                currentElement.click();
                return true;
            }
        }
        return false;
    }
    public void ready(){
        driver.findElement(By.id("ReadyBtn")).click();
        System.out.println("게임이 시작되면 엔터를 눌러주세요!");
        scanner.nextLine();
    }


    public void send(String msg){
        driver.findElement(By.xpath("//*[@id=\"Talk\"]")).sendKeys(msg);
        driver.findElement(By.xpath("//*[@id=\"ChatBtn\"]")).click();
    }

    public String getWords(char start){
        if (dataLoad.map.containsKey(start)){
            String  tmp = dataLoad.map.get(start).get(0);
            dataLoad.map.get(start).remove(0);
            return tmp;
        }else{
            return "";
        }
    }

    public ArrayList<String> now(){
        currentElement = driver.findElement(By.xpath("//*[@id=\"Middle\"]/div[27]/div/div[1]/div[5]/div/div[1]"));
        String tmp = currentElement.getText();
        ArrayList<String> strList = new ArrayList<>();
        if(tmp.contains("(")){
            String[] strTmp = tmp.split("\\(");
            for(String str: strTmp){
                strList.add(str.replace("\\)",""));
            }
        }else{
            strList.add(tmp);
        }
        return strList;
    }

    public void attack(){
        boolean first = true;
        for(String ch : now()){
            currentElements = driver.findElements(By.className("game-user-current"));
            if(currentElements.size() == 0){
                return;
            }
            currentElements = currentElements.get(0).findElements(By.className("game-user-name"));
            String player_name = currentElements.get(0).getText();
            if(currentElements.size() == 0 && !player_name.equals(ID)){
                return;
            }
            String word = getWords(ch.charAt(0));
            if(word.equals("")){
                if(!first){
                    try {
                        driver.wait(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return;
                }
            }
            System.out.println(word);
            send(word);
            first = false;
        }
    }


    public void auto(){
        while(true){
            currentElements = driver.findElements(By.className("game-user-current"));
            if(currentElements.size() == 0){
                continue;
            }
            currentElements = driver.findElements(By.className("game-user-name"));
            String player_name = currentElements.get(0).getText();
            if(currentElements.size() == 0 && !player_name.equals(ID)){
                continue;
            }
            attack();
        }
    }

    public void close(){
        scanner.close();
        driver.close();
    }

}
