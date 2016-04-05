/*
 * JNeuralNetwork.java
 *
 * Created on April 6, 2009, 2:10 AM
 */

package org.neuroph.netbeans.main.easyneurons.view;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.neuroph.core.Layer;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.Neuron;
import org.neuroph.util.NeuralNetworkType;
import sun.swing.SwingUtilities2;

/**
 *
 * @author  Zoran Sevarac <sevarac@gmail.com>
 */
public class JNeuralNetwork extends JPanel {
	private static final long serialVersionUID = 1L;
        private int maxNeurons = 100;
	
	NeuralNetwork<?> neuralNet;
	NetworkLayout netLayout;	
	
    /** Creates new form JNeuralNetwork */
    public JNeuralNetwork() {
        initComponents();
    }
    
	public void setNetwork(NeuralNetwork neuralNet) {
		this.neuralNet = neuralNet;
		this.setNetLayout(neuralNet.getNetworkType());
		this.updateView();
	}

	private void setNetLayout(NeuralNetworkType type) {

        if (type == null){
            this.netLayout = new RowLayersLayout();
            return;
        }

		switch (type) {
		case ADALINE:
			this.netLayout = new RowLayersLayout();
			break;
		case PERCEPTRON:
			this.netLayout = new RowLayersLayout();
			break;
		case MULTI_LAYER_PERCEPTRON:
			this.netLayout = new RowLayersLayout();
			break;
		case HOPFIELD:
			this.netLayout = new SquareLayersLayout();
			break;
		case KOHONEN:
			this.netLayout = new KohonenLayout();
			break;
		case NEURO_FUZZY_REASONER:
			this.netLayout = new RowLayersLayout();
			break;

		default:
			this.netLayout = new RowLayersLayout();
			break;
		}
	}

	public void update() {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                            updateView();
                    }
                });
	}

        // this should be done only when setting neural network
        // I shoud not repaint all componnets for each update
        // instead I should update only neuron values
	public void updateView() {
		this.removeAll();
		GridBagConstraints gb = new GridBagConstraints();

		gb.gridx = 0;
		gb.gridy = 0;
		gb.fill= GridBagConstraints.HORIZONTAL;
		gb.insets = new Insets(0, 0, 5 , 0);
		
		int i = 0;
		for(Layer layer : neuralNet.getLayers()) {
			int layerLayout = netLayout.getLayerLayout(i);
			JLayer layerPanel;
			if (layerLayout == NetworkLayout.ROW_LAYOUT)
				layerPanel = new JLayer();
			else {
				int neuronsCount = layer.getNeuronsCount();
				layerPanel = new JLayer(neuronsCount);
			}
			layerPanel.setLayerIndex(i);
			i++;

                        if (layer.getNeuronsCount() < maxNeurons) {
                            for (Neuron neuron : layer.getNeurons()) {
                                    layerPanel.add(new JNeuron(neuron));
                            }
                        } else {
                            layerPanel.add(new JLabel("Too many neurons to display ("+layer.getNeuronsCount()+")..."));
                        }

			this.add(layerPanel, gb);
			
			gb.gridy += 1;
		} // for layers
		
		this.revalidate();
	}
    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
        private void initComponents() {

                setName("Form"); // NOI18N
                setLayout(new java.awt.GridBagLayout());
        }// </editor-fold>//GEN-END:initComponents


        // Variables declaration - do not modify//GEN-BEGIN:variables
        // End of variables declaration//GEN-END:variables

}
