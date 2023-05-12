package hr.fer.zemris.display;

import hr.fer.zemris.Animation;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class DisplayFrame extends JFrame {
    JSpinner noOfFrames = new JSpinner();
    JSpinner noOfRows = new JSpinner();
    JSpinner noOfCols = new JSpinner();
    ImagePanel[] animationDisplay;

    public DisplayFrame(){
        super();

        setTitle("Animation generator");
        setLocation(400, 400);
        setSize(700, 700);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        initGui();
    }

    private void initGui(){
        getContentPane().setLayout(new GridLayout(2, 1));

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new GridLayout(1,3));

        textPanel.add("frame count", noOfFrames);
        textPanel.add("row count", noOfRows);
        textPanel.add("column count", noOfCols);

        getContentPane().add(textPanel);

        JButton generate = new JButton("Generate animation");
        getContentPane().add(generate);

        generate.addActionListener(e -> {
            try {
                int frameCount = (int) noOfFrames.getValue();
                int rowCount = (int) noOfRows.getValue();
                int colCount = (int) noOfCols.getValue();

                if (frameCount<=0 || rowCount<=0 || colCount<=0) throw new IllegalArgumentException();

                Animation animation=new Animation(frameCount, colCount, rowCount);
                int frameNo=1;
                while (!animation.done()){
                    animation.newFrame();
                    System.out.println("Done with frame "+frameNo++);
                }

                animationDisplay=new ImagePanel[frameCount];

                getContentPane().removeAll();
                setLayout(new GridLayout(1, 1));

                //BufferedImage[] images=new BufferedImage[frameCount];

                for (int k=0; k<frameCount; ++k) {
                    animationDisplay[k] = new ImagePanel(rowCount, colCount, animation.getFrames()[k], k);
                    //images[k]=animationDisplay[k].getImg();
                }

                setTitle("Last animation frame");
                //JScrollPane scrollPane = new JScrollPane();
                //scrollPane.setLayout(new BorderLayout());
                //getContentPane().add(scrollPane);
                //scrollPane.add(animationDisplay[frameCount-1]);
                getContentPane().add(animationDisplay[frameCount-1]);

                setSize(100*colCount, 100*rowCount+30);

                for (int k=0; k<frameCount; ++k) {
                    File outputfile = new File("./frame"+k+".png");
                    try {
                        ImageIO.write(animationDisplay[k].getImg(), "png", outputfile);
                    } catch (IOException ex) {
                        //throw new RuntimeException(ex);
                    }
                }

            }
            catch (RuntimeException ex){
                System.out.println("Doslo je do greske");
            }

            noOfFrames.setValue(0);
            noOfRows.setValue(0);
            noOfCols.setValue(0);
        });
    }

    public static void main(String[] args){
        new DisplayFrame().setVisible(true);
    }
}
