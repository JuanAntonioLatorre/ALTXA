package org.statistical.analysis.visual.components;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.statistical.analysis.pojo.ZetaTest;
import org.statistical.analysis.pojo.ZetaTestXYItem;

import javax.swing.*;
import java.awt.*;

public class ScatterPlotFrame extends JFrame {

    public ScatterPlotFrame(String title, ZetaTest zetaTest) {
        super(title);

        XYDataset dataset = createDataset(zetaTest);
        JFreeChart chart = ChartFactory.createScatterPlot(
                "ZetaTest results",
                zetaTest.getTextAName() + "-markers",
                zetaTest.getTextBName() + "-markers",
                dataset);

        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(new Color(255, 255, 255));

        XYSplineRenderer renderer = new XYSplineRenderer();
        renderer.setLinesVisible(false);

        plot.setRenderer(0, renderer);
        plot.getRendererForDataset(plot.getDataset(0)).setSeriesPaint(0, Color.red);
        plot.getRendererForDataset(plot.getDataset(0)).setSeriesPaint(1, Color.BLUE);
        plot.getRendererForDataset(plot.getDataset(0)).setSeriesPaint(2, Color.BLACK);


        ChartPanel panel = new ChartPanel(chart);
        setContentPane(panel);
    }

    private XYDataset createDataset(ZetaTest zetaTest) {
        XYSeriesCollection dataset = new XYSeriesCollection();

        XYSeries series1 = new XYSeries(zetaTest.getTextAName());
        for (ZetaTestXYItem item : zetaTest.getTextAGraphItems())
            series1.add(item.getPercentageAuthorA(), item.getPercentageAuthorB());

        dataset.addSeries(series1);

        XYSeries series2 = new XYSeries(zetaTest.getTextBName());
        for (ZetaTestXYItem item : zetaTest.getTextBGraphItems())
            series2.add(item.getPercentageAuthorA(), item.getPercentageAuthorB());

        dataset.addSeries(series2);

        XYSeries series3 = new XYSeries(zetaTest.getTextCName());
        for (ZetaTestXYItem item : zetaTest.getTextCGraphItems())
            series3.add(item.getPercentageAuthorA(), item.getPercentageAuthorB());

        dataset.addSeries(series3);
        return dataset;
    }
}