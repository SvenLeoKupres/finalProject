package hr.fer.zemris.display;

import com.squareup.gifencoder.FloydSteinbergDitherer;
import com.squareup.gifencoder.GifEncoder;
import com.squareup.gifencoder.ImageOptions;
import hr.fer.zemris.Animation;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

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

                //setTitle("Last animation frame");
                //JScrollPane scrollPane = new JScrollPane();
                //scrollPane.setLayout(new BorderLayout());
                //getContentPane().add(scrollPane);
                //scrollPane.add(animationDisplay[frameCount-1]);
                //getContentPane().add(animationDisplay[frameCount-1]);

                //setSize(100*colCount, 100*rowCount+30);
                //noOfFrames.setValue(0);
                //noOfRows.setValue(0);
                //noOfCols.setValue(0);

                /*
                for (int k=0; k<frameCount; ++k) {
                    File outputfile = new File("./frame"+k+".png");
                    try {
                        ImageIO.write(animationDisplay[k].getImg(), "png", outputfile);
                    } catch (IOException ex) {
                        //throw new RuntimeException(ex);
                    }
                }
                 */

                try (FileOutputStream outputStream = new FileOutputStream("animation.gif")) {
                    ImageOptions options = new ImageOptions();

                    //Set delay between each frame
                    options.setDelay(1, TimeUnit.SECONDS);
                    //Use Floyd Steinberg dithering as it yields the best quality
                    options.setDitherer(FloydSteinbergDitherer.INSTANCE);

                    GifEncoder encoder = new GifEncoder(outputStream, 100*colCount, 100*rowCount, 0);

                    for (int k=0; k<frameCount; ++k){
                        encoder.addImage(convertImageToArray(animationDisplay[k].getImg()), options);
                    }
                    encoder.finishEncoding();

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
            catch (RuntimeException ex){
                System.out.println("Doslo je do greske");
            }

            System.exit(0);
        });
    }

    /**
     * Convert BufferedImage into RGB pixel array
     */
    public int[][] convertImageToArray(BufferedImage image) throws IOException {
        int[][] rgbArray = new int[image.getHeight()][image.getWidth()];
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                rgbArray[i][j] = image.getRGB(j, i);
            }
        }
        return rgbArray;
    }

    public static void main(String[] args){
        new DisplayFrame().setVisible(true);
    }
}
