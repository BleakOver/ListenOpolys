package listenopolys.models;

import javafx.application.Platform;
import javafx.scene.chart.XYChart;
import javafx.scene.media.AudioSpectrumListener;

import java.util.Arrays;

public class SpektrumListener implements AudioSpectrumListener {
    private final double DROPDOWN = 4;
    private final int BANDS;
    TrackReader reader;
    private XYChart.Data[] series1Data;

    float[] buffer;


    public SpektrumListener(TrackReader reader, XYChart.Data[] graph, int BANDS){
        series1Data = graph;
        this.BANDS = BANDS;
        this.reader = reader;
        buffer = createFilledBuffer(BANDS, reader.getPlayer().getAudioSpectrumThreshold());
    }

    public void spectrumDataUpdate(double timestamp, double duration, float[] magnitudes, float[] phases) {
        Platform.runLater(() -> {
            series1Data[0].setYValue(0);
            series1Data[BANDS + 1].setYValue(0);
            for (int i = 0; i < magnitudes.length; i++) {
                if (magnitudes[i] >= buffer[i]) {
                    buffer[i] = magnitudes[i];
                    series1Data[i + 1].setYValue(magnitudes[i] - reader.getPlayer().getAudioSpectrumThreshold());
                } else {
                    series1Data[i + 1].setYValue(buffer[i] - reader.getPlayer().getAudioSpectrumThreshold());
                    buffer[i] -= DROPDOWN;
                }
            }
        });
    }

    private float[] createFilledBuffer(int size, float filledValue){
        float [] floats = new float[size];
        Arrays.fill(floats, filledValue);
        return floats;
    }
}
