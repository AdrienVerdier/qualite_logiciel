package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;

import controler.gestionProduit;
import controler.gestionUtilisateur;
import controler.myTableProduitManagement;

/**
 * Cette classe repr�sente le panel qui permet de g�rer les produits
 * @author Pierre Savary & Adrien Verdier
 *
 */
public class AffichageProduitPanel extends JPanel implements ActionListener {
	
	private static final long serialVersionUID = 4L;
	private JButton addButton, suppressButton, changerMotDePasse, retour, ouvrir;
	private JFrame frame;
	private JTable table;
	private String police = "Arial";
	private myTableProduitManagement tablemodel;
	private JLabel label;
	private int idUser;
	private boolean isChefMagasin;
	private int idRayon;

	/**
	 * Cette m�thode permet de cr�er les objets qui vont �tre pr�sent sur le panel
	 * @param frame la frame sur laquelle doit s'afficher le panel
	 * @param idUser identifiant de l'utilisateur connect� � l'application
	 * @param isChefMagasin bool�en pr�cisant si l'utilisateur connect� est un chef de magasin
	 * @param idRayon identifiant du rayon dans lequel on se trouve
	 */
	public AffichageProduitPanel(JFrame frame, int idUser, boolean isChefMagasin , int idRayon) {
		this.idUser = idUser;
		this.isChefMagasin = isChefMagasin;
		this.idRayon = idRayon;
		this.frame = frame;

		this.setLayout(null);
		this.frame.setContentPane(this);

		label = new JLabel("Visionnage des produits");
		label.setLayout(null);
		label.setFont(new Font(police, Font.BOLD, 20));
		label.setBounds(25, 0, 250, 40);
		this.add(label);

		addButton = new JButton("Ajouter");
		addButton.setBounds(appInterface.windowsSizeX - 225, appInterface.windowsSizeY - 500, 175, 50);
		addButton.setFont(new Font(police, Font.BOLD, 20));
		addButton.setForeground(Color.BLACK);
		addButton.setBackground(Color.LIGHT_GRAY);
		this.add(addButton);
		addButton.addActionListener(this);
		if(isChefMagasin) {
			addButton.setVisible(true);
		}
		else if (gestionUtilisateur.getRayonChefRayon(idUser) == idRayon){
			addButton.setVisible(true);
		}
		else {
			addButton.setVisible(false);
		}

		String title[] = { "ID Produit", "Nom du Produit"};

		tablemodel = new myTableProduitManagement(title, idRayon);
		table = new JTable(tablemodel);
		tablemodel.setTable(table);
		JScrollPane tableContainer = new JScrollPane(table);
		table.getTableHeader().setFont(new Font(police, Font.BOLD, 13));
		table.getTableHeader().setForeground(Color.blue);
		table.getTableHeader().setBackground(new Color(51, 153, 255));
		table.setFont(new Font("Arial", Font.BOLD, 13));
		table.setAutoCreateRowSorter(true);
		table.getTableHeader().setReorderingAllowed(false);
		tableContainer.setBounds(50, 200, 700, 400);
		this.add(tableContainer, BorderLayout.CENTER);

		suppressButton = new JButton("Supprimer");
		suppressButton.setBounds(appInterface.windowsSizeX - 225, appInterface.windowsSizeY - 425, 175, 50);
		suppressButton.setFont(new Font(police, Font.BOLD, 20));
		suppressButton.setForeground(Color.BLACK);
		suppressButton.setBackground(Color.LIGHT_GRAY);
		this.add(suppressButton);
		suppressButton.addActionListener(this);
		if(isChefMagasin) {
			suppressButton.setVisible(true);
		}
		else if (gestionUtilisateur.getRayonChefRayon(idUser) == idRayon){
			suppressButton.setVisible(true);
		}
		else {
			suppressButton.setVisible(false);
		}
		
		changerMotDePasse = new JButton("Changer Mot de Passe");
		changerMotDePasse.setBounds(appInterface.windowsSizeX - 450, appInterface.windowsSizeY - 125, 400, 50);
		changerMotDePasse.setFont(new Font(police, Font.BOLD, 20));
		changerMotDePasse.setForeground(Color.BLACK);
		changerMotDePasse.setBackground(Color.LIGHT_GRAY);
		this.add(changerMotDePasse);
		changerMotDePasse.addActionListener(this);
		
		ouvrir = new JButton("Visionner");
		ouvrir.setBounds(appInterface.windowsSizeX - 275, 75, 225, 50);
		ouvrir.setFont(new Font(police, Font.BOLD, 20));
		ouvrir.setForeground(Color.BLACK);
		ouvrir.setBackground(Color.LIGHT_GRAY);
		this.add(ouvrir);
		ouvrir.addActionListener(this);
		
		retour = new JButton("retour");
		retour.setBounds(125, appInterface.windowsSizeY - 125, 175, 50);
		retour.setFont(new Font(police, Font.BOLD, 20));
		retour.setForeground(Color.BLACK);
		retour.setBackground(Color.LIGHT_GRAY);
		this.add(retour);
		retour.addActionListener(this);
	}

	/**
	 * This method gathers the different action of the panel
	 * 
	 * @param e The action that is performed
	 */
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == addButton) {
			
			JPanel ajouterProduitPanel = new AjouterProduitPanel(frame, idUser, isChefMagasin, idRayon, true, 0);
			frame.repaint();
			frame.revalidate();
			
		}

		if (e.getSource() == suppressButton) {
			if (table.getSelectedRow() != -1) {
				int retour = JOptionPane.showConfirmDialog(this,
						"�tes-vous s�r de vouloir supprimer ce rayon",
						"CONFIRM", JOptionPane.YES_NO_OPTION);

				if (retour == JOptionPane.OK_OPTION)
					tablemodel.removeRow(table.getSelectedRow());
			}
		}
		
		if (e.getSource() == ouvrir) {
			JPanel visionnageProduitPanel = new VisionnageProduitPanel(frame, idUser, isChefMagasin, idRayon, (Integer)table.getValueAt(table.getSelectedRow(), 0));
			frame.repaint();
			frame.revalidate();
		}
		
		if (e.getSource() == retour) {
			JPanel affichageRayon = new AffichageRayon(frame, isChefMagasin, idUser);
			frame.repaint();
			frame.revalidate();
		}
		
		if (e.getSource() == changerMotDePasse) {
			String newPasswd = JOptionPane.showInputDialog(null, "Entrer votre nouveau mot de passe", "Changement mot de passe", JOptionPane.QUESTION_MESSAGE);
			if(!newPasswd.equals("")) {
				gestionUtilisateur.changerMDP(idUser, isChefMagasin, newPasswd);
				JOptionPane.showMessageDialog(null, "Mot de passe chang�");
			}
		}
	}

}
