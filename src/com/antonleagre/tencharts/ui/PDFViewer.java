package com.antonleagre.tencharts.ui;

import com.antonleagre.tencharts.charts.Chart;
import com.antonleagre.tencharts.charts.PDFDownloader;
import javafx.embed.swing.SwingNode;
import org.icepdf.ri.common.MyAnnotationCallback;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import org.icepdf.ri.util.PropertiesManager;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;

public class PDFViewer extends SwingNode {

    private SwingController swingController;


    public PDFViewer(){
        super();
        try {
            swingController = new SwingController();
            SwingUtilities.invokeAndWait(() -> {
                swingController.setIsEmbeddedComponent(true);

                PropertiesManager properties = new PropertiesManager(System.getProperties(), swingController.getMessageBundle());
                properties.setFloat(PropertiesManager.PROPERTY_DEFAULT_ZOOM_LEVEL, 1.25f);

                // hide the status bar
                properties.setBoolean(PropertiesManager.PROPERTY_VIEWPREF_HIDETOOLBAR, true);
                properties.setBoolean(PropertiesManager.PROPERTY_SHOW_TOOLBAR_ROTATE, true);
                properties.setBoolean(PropertiesManager.PROPERTY_VIEWPREF_HIDEMENUBAR, true);
                properties.setBoolean(PropertiesManager.PROPERTY_HIDE_UTILITYPANE, true);

                properties.setBoolean(PropertiesManager.PROPERTY_SHOW_STATUSBAR_VIEWMODE, false);
                properties.setBoolean(PropertiesManager.PROPERTY_SHOW_STATUSBAR_STATUSLABEL, false);

                swingController.getDocumentViewController().setAnnotationCallback(new MyAnnotationCallback(swingController.getDocumentViewController()));

                SwingViewBuilder factory = new SwingViewBuilder(swingController, properties);
                JPanel viewerP = factory.buildViewerPanel();
                viewerP.setPreferredSize(new Dimension(600,400));
                viewerP.revalidate();

                setContent(viewerP);

                System.out.println("ALL PDF RELATED STUFF IS DONE");
            });

        } catch (InterruptedException | InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    public void openChart(Chart chart){
       // swingController.openDocument(chart.getLocalLocation());
        //swingController.openDocument(PDFDownloader.downloadSingleChart(chart).getLocalLocation());
        //swingController.openDocument(PDFDownloader.downloadSingleChartCommons(chart).getLocalLocation());
        swingController.openDocument(PDFDownloader.downloadSingleChartHTTPS(chart).getLocalLocation());
        // TODO: 5/5/2018 check nullpointers here or in the downloader class
    }
}
