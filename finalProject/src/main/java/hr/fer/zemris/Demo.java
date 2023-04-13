package hr.fer.zemris;

public class Demo {
    public static void main(String[] args){
        Animation animation = new Animation(1, 5, 5);
        while (!animation.done()){
            animation.newFrame();
        }
    }
}
