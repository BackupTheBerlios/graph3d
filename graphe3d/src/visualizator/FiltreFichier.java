package visualizator;


import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * Cette classe sert a filtrer les fichiers.
 * 
 * @author Jérôme Catric
 * @author Mohamed Boutahri
 */
public class FiltreFichier extends FileFilter {

	/**
	 * Description des fichiers acceptés.
	 */
	private String description;

	/**
	 * Extension des fichiers acceptés.
	 */
	private String[] extension;

	/**
	 * Constructeur de la classe FiltreFichier.
	 * 
	 * @param desc
	 *            La description associe a l'extension.
	 * @param ext
	 *            L'extention.
	 */
	public FiltreFichier(final String desc, final String[] ext) {
		this.description = desc;
		this.extension = ext;
	}

	/**
	 * Methode servant a savoir si le fichier passe le filtre.
	 * 
	 * @param file
	 *            Le fichier à tester
	 * @return Retourne true si le fichier est conforme au filtre.
	 */
	public final boolean accept(final File file) {
		if (file.isDirectory()) {
			return true;
		}
		
		String suffixe = null;
		String s = file.getName();
		int i = s.lastIndexOf('.');

		if (i > 0 && i < s.length() - 1) {
			suffixe = s.substring(i + 1).toLowerCase();
		}
		return suffixe != null && appartient(suffixe);
	}

	/**
	 * Fonction testant si une extension est contenu dans le tableau d'extension.
	 * 
	 * @param suffixe l'extension a tester.
	 * @return True si cette extension est dans le tableau d'extension.
	 */
	private boolean appartient(final String suffixe) {
		for (int i = 0; i < extension.length; ++i) {
			if (suffixe.equals(extension[i])) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Methode retournant la description.
	 * 
	 * @return retourne la description.
	 */
	public final String getDescription() {
		return description;
	}

}
