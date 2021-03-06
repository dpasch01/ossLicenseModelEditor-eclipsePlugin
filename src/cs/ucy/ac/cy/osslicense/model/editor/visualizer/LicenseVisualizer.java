package cs.ucy.ac.cy.osslicense.model.editor.visualizer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import javax.swing.JFrame;
import org.apache.commons.collections15.Transformer;

import cs.ucy.ac.cy.osslicense.model.editor.model.LicenseModel;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.AbstractModalGraphMouse;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;

public class LicenseVisualizer {

	private LicenseModel licenseModel;
	private Graph<String, Edge> licenseGraph;
	private boolean isDirty;
	private LicenseTermPopupMenu<String, Edge> popupMenu;

	public LicenseTermPopupMenu<String, Edge> getPopupMenu() {
		return this.popupMenu;
	}

	public LicenseVisualizer(LicenseModel lModel) {
		this.setLicenseModel(lModel);
		this.setLicenseGraph(new SparseMultigraph<String, Edge>());

		licenseGraph.addVertex(lModel.getLicenseIdentifier());

		for (String right : lModel.getRights()) {
			licenseGraph.addVertex(right);
			licenseGraph.addEdge(new Edge("hasRight"), lModel.getLicenseIdentifier(), right);
		}

		for (String obligation : lModel.getObligations()) {
			licenseGraph.addVertex(obligation);
			licenseGraph.addEdge(new Edge("hasObligation"), lModel.getLicenseIdentifier(), obligation);
		}

		for (String additionalCondition : lModel.getAdditionalConditions()) {
			licenseGraph.addVertex(additionalCondition);
			licenseGraph.addEdge(new Edge("hasAdditionalCondition"), lModel.getLicenseIdentifier(),
					additionalCondition);
		}
	}

	public void displayLicenseGraph() {

		Transformer<String, Paint> vertexColor = new Transformer<String, Paint>() {
			public Paint transform(String licenseTerm) {
				if (licenseTerm.contains("May"))
					return Color.GREEN;
				if (licenseTerm.contains("Must"))
					return Color.ORANGE;
				if (licenseTerm.equals("LimitedLiability") || licenseTerm.equals("ProvideWithoutWarranty"))
					return Color.CYAN;
				return Color.YELLOW;
			}
		};

		Layout<String, Edge> layout = new CircleLayout<String, Edge>(this.licenseGraph);
		layout.setSize(new Dimension(1024, 768));
		VisualizationViewer<String, Edge> vv = new VisualizationViewer<String, Edge>(layout);
		vv.setPreferredSize(new Dimension(1024, 768));

		vv.getRenderContext().setVertexFillPaintTransformer(vertexColor);
		vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<String>());
		vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller<Edge>());

		AbstractModalGraphMouse gm = new DefaultModalGraphMouse<String, Edge>();
		vv.setGraphMouse(gm);

		LicenseTermPopupMenu<String, Edge> popupMenu = new LicenseTermPopupMenu<>(licenseModel);
		gm.add(popupMenu);

		JFrame frame = new JFrame("Interactive Graph View 1");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(vv);

		frame.pack();
		frame.setVisible(true);

	}

	public VisualizationViewer<String, Edge> generateGraphView() {

		Transformer<String, Paint> vertexColor = new Transformer<String, Paint>() {
			public Paint transform(String licenseTerm) {
				if (licenseTerm.contains("May"))
					return Color.GREEN;
				if (licenseTerm.contains("Must"))
					return Color.ORANGE;
				if (licenseTerm.equals("LimitedLiability") || licenseTerm.equals("ProvideWithoutWarranty"))
					return Color.CYAN;
				return Color.YELLOW;
			}
		};

		Layout<String, Edge> layout = new CircleLayout<String, Edge>(this.licenseGraph);
		layout.setSize(new Dimension(1024, 768));
		VisualizationViewer<String, Edge> vv = new VisualizationViewer<String, Edge>(layout);
		vv.setPreferredSize(new Dimension(1024, 768));

		vv.getRenderContext().setVertexFillPaintTransformer(vertexColor);
		vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<String>());
		vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller<Edge>());

		AbstractModalGraphMouse gm = new DefaultModalGraphMouse<String, Edge>();
		vv.setGraphMouse(gm);

		popupMenu = new LicenseTermPopupMenu<>(licenseModel);
		gm.add(popupMenu);

		return vv;
	}

	public LicenseModel getLicenseModel() {
		return licenseModel;
	}

	public void setLicenseModel(LicenseModel licenseModel) {
		this.licenseModel = licenseModel;
	}

	public Graph<String, Edge> getLicenseGraph() {
		return licenseGraph;
	}

	public void setLicenseGraph(Graph<String, Edge> licenseGraph) {
		this.licenseGraph = licenseGraph;
	}

	public boolean isDirty() {
		return isDirty;
	}

	public void setDirty(boolean isDirty) {
		this.isDirty = isDirty;
	}

	public void setPopupMenuModel(LicenseModel lModel) {
		this.popupMenu.setLicenseModel(lModel);
	}
}
