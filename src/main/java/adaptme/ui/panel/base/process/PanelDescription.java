package adaptme.ui.panel.base.process;

import java.awt.Color;
import java.awt.Font;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;

import org.eclipse.epf.uma.Process;
import org.eclipse.epf.uma.ProcessDescription;

public class PanelDescription extends JPanel {

    private static final long serialVersionUID = -1767090327713486112L;
    private JTextField textFieldName;
    private JTextField textFieldPresentationName;
    private JTextPane textAreaMainDescription;
    private JTextPane textAreaAlternativas;
    private JTextPane textAreaKeyConsiderations;
    private JTextPane textAreaScope;
    private JTextPane textAreaPurpose;
    private JTextPane textAreaBriefDescription;
    private JTextPane textAreaUsageNotes;
    private JTextPane textAreaHowtoStaff;

    private Process process;

    public PanelDescription(Process process) {

	this.process = process;

	initComponents();
    }

    private void initComponents() {

	setBackground(Color.WHITE);

	JLabel lblGeneralInformation = new JLabel("General Information");
	lblGeneralInformation.setFont(new Font("Tahoma", Font.PLAIN, 13));
	lblGeneralInformation.setForeground(Color.BLUE);

	JLabel lblProvideGeneralInformation = new JLabel("Provide general information about this process.");

	JLabel lblName = new JLabel("Name:");

	textFieldName = new JTextField();
	textFieldName.setColumns(10);

	JLabel lblPresentationName = new JLabel("Presentation name:");

	textFieldPresentationName = new JTextField();
	textFieldPresentationName.setColumns(10);

	JLabel lblBriefDescription = new JLabel("Brief description:");

	JScrollPane scrollPane = new JScrollPane();
	scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

	JLabel lblPurpose = new JLabel("Purpose:");

	JScrollPane scrollPane_1 = new JScrollPane();
	scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

	JLabel lblDetailInformation = new JLabel("Detail Information");
	lblDetailInformation.setFont(new Font("Tahoma", Font.PLAIN, 13));
	lblDetailInformation.setForeground(Color.BLUE);

	JLabel lblProvideDetailedInformation = new JLabel("Provide detailed information about this process.");

	JLabel lblMainDescription = new JLabel("Main description:");

	JScrollPane scrollPane_2 = new JScrollPane();
	scrollPane_2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

	JLabel lblScope = new JLabel("Scope:");

	JScrollPane scrollPane_3 = new JScrollPane();
	scrollPane_3.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

	JLabel lblUsageNotes = new JLabel("Usage Notes:");

	JScrollPane scrollPane_4 = new JScrollPane();
	scrollPane_4.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

	JLabel lblAlternativas = new JLabel("Alternativas:");

	JScrollPane scrollPane_5 = new JScrollPane();
	scrollPane_5.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

	JLabel lblHowToStaff = new JLabel("How to Staff:");

	JScrollPane scrollPane_6 = new JScrollPane();
	scrollPane_6.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

	JLabel lblKeyConsiderations = new JLabel("<html> Key<br/> Considerations:</html>");

	JScrollPane scrollPane_7 = new JScrollPane();
	scrollPane_7.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

	textAreaKeyConsiderations = new JTextPane();
	textAreaKeyConsiderations.setContentType("text/html");
	scrollPane_7.setViewportView(textAreaKeyConsiderations);

	textAreaHowtoStaff = new JTextPane();
	textAreaHowtoStaff.setContentType("text/html");
	scrollPane_6.setViewportView(textAreaHowtoStaff);

	textAreaAlternativas = new JTextPane();
	textAreaAlternativas.setContentType("text/html");
	scrollPane_5.setViewportView(textAreaAlternativas);

	textAreaUsageNotes = new JTextPane();
	textAreaUsageNotes.setContentType("text/html");
	scrollPane_4.setViewportView(textAreaUsageNotes);

	textAreaScope = new JTextPane();
	textAreaScope.setContentType("text/html");
	scrollPane_3.setViewportView(textAreaScope);

	textAreaMainDescription = new JTextPane();
	textAreaMainDescription.setContentType("text/html");
	scrollPane_2.setViewportView(textAreaMainDescription);

	textAreaPurpose = new JTextPane();
	textAreaPurpose.setContentType("text/html");
	scrollPane_1.setViewportView(textAreaPurpose);

	textAreaBriefDescription = new JTextPane();
	textAreaBriefDescription.setContentType("text/html");
	scrollPane.setViewportView(textAreaBriefDescription);
	GroupLayout groupLayout = new GroupLayout(this);
	groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
		.addGroup(groupLayout
			.createSequentialGroup().addGap(
				6)
			.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(lblGeneralInformation)
				.addComponent(lblProvideGeneralInformation, GroupLayout.PREFERRED_SIZE, 310,
					GroupLayout.PREFERRED_SIZE)
			.addGroup(
				groupLayout.createSequentialGroup()
					.addComponent(lblName, GroupLayout.PREFERRED_SIZE, 50,
						GroupLayout.PREFERRED_SIZE)
					.addGap(84).addComponent(this.textFieldName, GroupLayout.DEFAULT_SIZE, 300,
						Short.MAX_VALUE))
			.addGroup(groupLayout.createSequentialGroup()
				.addComponent(lblPresentationName, GroupLayout.PREFERRED_SIZE, 130,
					GroupLayout.PREFERRED_SIZE)
				.addGap(4).addComponent(this.textFieldPresentationName, GroupLayout.DEFAULT_SIZE, 300,
					Short.MAX_VALUE))
			.addGroup(groupLayout.createSequentialGroup()
				.addComponent(lblBriefDescription, GroupLayout.PREFERRED_SIZE, 116,
					GroupLayout.PREFERRED_SIZE)
				.addGap(18).addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE))
			.addGroup(groupLayout.createSequentialGroup()
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
					.addComponent(lblPurpose, GroupLayout.PREFERRED_SIZE, 66,
						GroupLayout.PREFERRED_SIZE)
					.addComponent(lblDetailInformation, GroupLayout.PREFERRED_SIZE, 124,
						GroupLayout.PREFERRED_SIZE))
				.addGap(10).addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE))
			.addComponent(lblProvideDetailedInformation, GroupLayout.PREFERRED_SIZE, 312,
				GroupLayout.PREFERRED_SIZE)
			.addGroup(groupLayout.createSequentialGroup()
				.addComponent(lblMainDescription, GroupLayout.PREFERRED_SIZE, 117,
					GroupLayout.PREFERRED_SIZE)
				.addGap(17).addComponent(scrollPane_2, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE))
			.addGroup(groupLayout.createSequentialGroup()
				.addComponent(lblScope, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
				.addGap(81).addComponent(scrollPane_3, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE))
			.addGroup(groupLayout.createSequentialGroup()
				.addComponent(lblUsageNotes, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
				.addGap(39).addComponent(scrollPane_4, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE))
			.addGroup(groupLayout.createSequentialGroup()
				.addComponent(lblAlternativas, GroupLayout.PREFERRED_SIZE, 88,
					GroupLayout.PREFERRED_SIZE)
				.addGap(46).addComponent(scrollPane_5, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE))
			.addGroup(groupLayout.createSequentialGroup()
				.addComponent(lblHowToStaff, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
				.addGap(46).addComponent(scrollPane_6, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE))
			.addGroup(groupLayout.createSequentialGroup()
				.addComponent(lblKeyConsiderations, GroupLayout.PREFERRED_SIZE, 103,
					GroupLayout.PREFERRED_SIZE)
				.addGap(31).addComponent(scrollPane_7, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)))
		.addGap(10)));
	groupLayout
		.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addGap(6)
					.addComponent(lblGeneralInformation).addGap(6)
					.addComponent(lblProvideGeneralInformation).addGap(6)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup().addGap(6)
							.addComponent(lblName))
						.addComponent(this.textFieldName, GroupLayout.PREFERRED_SIZE,
							GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		.addGap(6)
		.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
			.addGroup(groupLayout.createSequentialGroup().addGap(6).addComponent(lblPresentationName))
			.addComponent(this.textFieldPresentationName, GroupLayout.PREFERRED_SIZE,
				GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		.addGap(6)
		.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(lblBriefDescription)
			.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE))
		.addGap(6)
		.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
			.addGroup(groupLayout.createSequentialGroup().addGap(12).addComponent(lblPurpose).addGap(12)
				.addComponent(lblDetailInformation))
			.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE))
		.addGap(6).addComponent(lblProvideDetailedInformation).addGap(6)
		.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(lblMainDescription)
			.addComponent(scrollPane_2, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE))
		.addGap(12)
		.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(lblScope)
			.addComponent(scrollPane_3, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))
		.addGap(6)
		.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(lblUsageNotes)
			.addComponent(scrollPane_4, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))
		.addGap(6)
		.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(lblAlternativas)
			.addGroup(groupLayout.createSequentialGroup().addGap(1).addComponent(scrollPane_5,
				GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)))
		.addGap(6)
		.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
			.addGroup(groupLayout.createSequentialGroup().addGap(1).addComponent(lblHowToStaff))
			.addComponent(scrollPane_6, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))
		.addGap(6)
		.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(lblKeyConsiderations)
			.addComponent(scrollPane_7, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))));
	setLayout(groupLayout);
    }

    public String getMainDescription() {
	return textAreaMainDescription.getText();
    }

    public String getAlternativas() {
	return textAreaAlternativas.getText();
    }

    public String getKeyConsiderations() {
	return textAreaKeyConsiderations.getText();
    }

    public String getScope() {
	return textAreaScope.getText();
    }

    public String getTextFieldName() {
	return textFieldName.getText();
    }

    public String getPurpose() {
	return textAreaPurpose.getText();
    }

    public String getBriefDescription() {
	return textAreaBriefDescription.getText();
    }

    public String getUsageNotes() {
	return textAreaUsageNotes.getText();
    }

    public String getHowtoStaff() {
	return textAreaHowtoStaff.getText();
    }

    public String getPresentationName() {
	return textFieldPresentationName.getText();
    }

    public void setName(String text) {
	this.textFieldName.setText(text);
    }

    public void setPresentationName(String text) {
	this.textFieldPresentationName.setText(text);
    }

    public void setMainDescription(String text) {
	this.textAreaMainDescription.setText(text);
    }

    public void setAlternatives(String text) {
	this.textAreaAlternativas.setText(text);
    }

    public void setKeyConsiderations(String text) {
	this.textAreaKeyConsiderations.setText(text);
    }

    public void setScope(String text) {
	this.textAreaScope.setText(text);
    }

    public void setPurpose(String text) {
	this.textAreaPurpose.setText(text);
    }

    public void setBriefDescription(String text) {
	this.textAreaBriefDescription.setText(text);
    }

    public void setUsageNotes(String text) {
	this.textAreaUsageNotes.setText(text);
	;
    }

    public void setHowtoStaff(String text) {
	this.textAreaHowtoStaff.setText(text);
    }

    public void load() {
	setName(process.getName());
	setPresentationName(process.getPresentationName());
	ProcessDescription processDescription = (ProcessDescription) process.getPresentation();
	setMainDescription(processDescription.getMainDescription());
	setAlternatives(processDescription.getAlternatives());
	setKeyConsiderations(processDescription.getKeyConsiderations());
	setScope(processDescription.getScope());
	setPurpose(processDescription.getPurpose());
	setBriefDescription(process.getBriefDescription());
	setUsageNotes(processDescription.getUsageNotes());
	setHowtoStaff(processDescription.getHowToStaff());
    }

}
