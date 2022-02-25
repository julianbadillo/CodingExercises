import java.util.*;
import java.lang.*;
import java.io.*;

public class WordProcessor{


    public static void main(String args[]) throws IOException{
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String [] wordsOfInterest = in.readLine().split(" ");
        Word [] words = new Word[wordsOfInterest.length];

        //build words
        for(int i=0; i<words.length; i++)
            words[i] = new Word(wordsOfInterest[i].trim());

        //process stream
        do{
            //char by char
            char c = (char) in.read();
            for(Word w: words)
                w.update(c);
        }while(in.ready());
        //print results
        for(Word w: words)
            System.out.println(w);
    }


    static class Word{
        String w;
        int count=0;
        int i=0;
        boolean[] buff;

        Word(String w){
            this.w = w;
            buff = new boolean[w.length()];
        }
   
        void update(char c){
            //move the buffer
            for(int i=buff.length-1; i>0; i--)
                buff[i] = c == w.charAt(i) && buff[i-1];
            //first
            buff[0] = c == w.charAt(0);
            //if last is true, increase
            if(buff[buff.length-1]) count++;
        }

        public String toString(){
            return this.w+" "+this.count;
        }
    }


}
