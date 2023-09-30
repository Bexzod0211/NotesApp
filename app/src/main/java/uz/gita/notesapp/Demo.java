package uz.gita.notesapp;

public class Demo {
    int getA(){
        return DemoI.a;
    }


}


interface DemoI {
    int a = 1;
}