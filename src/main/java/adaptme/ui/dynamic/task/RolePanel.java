package adaptme.ui.dynamic.task;

import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import adaptme.ui.dynamic.RepositoryViewPanel;
import adaptme.ui.dynamic.UpdatePanel;
import model.spem.MethodContentRepository;

public class RolePanel implements UpdatePanel {
    private JLabel lblRole;
    private JLabel lblExperience;
    private JTextField textFieldExperience;
    private JLabel lblSkill;
    private JTextField textFieldSkill;
    private JLabel lblBestFitProbability;
    private JComboBox<String> comboBoxDistribution;
    private String title;
    private MethodContentRepository methodContentRepository;

    public RolePanel(RepositoryViewPanel repositoryViewPanel, MethodContentRepository methodContentRepository) {

	this.methodContentRepository = methodContentRepository;
	lblRole = new JLabel("RolePanel -");
	lblRole.setBounds(12, 40, 69, 15);
	lblRole.setFont(new Font("Tahoma", Font.BOLD, 12));

	lblExperience = new JLabel("Experience");
	lblExperience.setBounds(12, 85, 53, 14);

	textFieldExperience = new JTextField();
	textFieldExperience.setBounds(77, 79, 86, 20);
	textFieldExperience.setColumns(10);

	lblSkill = new JLabel("Skill");
	lblSkill.setBounds(227, 85, 17, 14);

	textFieldSkill = new JTextField();
	textFieldSkill.setBounds(256, 79, 86, 20);
	textFieldSkill.setColumns(10);

	lblBestFitProbability = new JLabel("Best fit probability distribution");
	lblBestFitProbability.setBounds(12, 122, 143, 14);

	comboBoxDistribution = new JComboBox<>();
	comboBoxDistribution.setBounds(256, 119, 164, 20);
	panel.setBorder(new TitledBorder(null, "Role", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	panel.setLayout(null);
	panel.add(lblRole);
	panel.add(lblExperience);
	panel.add(textFieldExperience);
	panel.add(lblSkill);
	panel.add(textFieldSkill);
	panel.add(lblBestFitProbability);
	panel.add(comboBoxDistribution);

	FocusListener focusListener = new FocusListener() {

	    @Override
	    public void focusLost(FocusEvent e) {
		repositoryViewPanel.setMessagem("");
	    }

	    @Override
	    public void focusGained(FocusEvent e) {
		repositoryViewPanel.setMessagem("Não existe dados no servidor para " + title);
	    }
	};
	textFieldExperience.addFocusListener(focusListener);
	textFieldSkill.addFocusListener(focusListener);
    }

    public double getExperience() {
	return Double.parseDouble(textFieldExperience.getText());
    }

    public void setExperience(JTextField textFieldExperience) {
	this.textFieldExperience = textFieldExperience;
    }

    public double getSkill() {
	return Double.parseDouble(textFieldSkill.getText());
    }

    public void setSkill(double textFieldSkill) {
	this.textFieldSkill.setText("" + textFieldSkill);
    }

    public String getComboBoxDistribution() {
	return (String) comboBoxDistribution.getSelectedItem();
    }

    public void setDistribution(List<String> list) {
	DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(list.toArray(new String[list.size()]));
	comboBoxDistribution.setModel(model);
    }

    public void setRoleLabel(String title) {
	this.title = title;
	lblRole.setText(title);
    }

    private JPanel panel = new JPanel();

    public JPanel getPanel() {
    	return panel;
    }

	@Override
	public void updateContent() {
		// TODO Auto-generated method stub
		
	}
}
