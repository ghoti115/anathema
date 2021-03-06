package net.sf.anathema.charmtree;

import net.disy.commons.swing.layout.grid.GridDialogLayout;
import net.disy.commons.swing.layout.grid.GridDialogLayoutData;
import net.sf.anathema.charmtree.presenter.view.CharmTreeRenderer;
import net.sf.anathema.charmtree.presenter.view.ICascadeSelectionView;
import net.sf.anathema.charmtree.presenter.view.ICharmGroupChangeListener;
import net.sf.anathema.charmtree.presenter.view.SvgCharmTreeRenderer;
import net.sf.anathema.lib.control.ObjectValueListener;
import net.sf.anathema.lib.gui.action.SmartAction;
import net.sf.anathema.lib.gui.widgets.ChangeableJComboBox;
import net.sf.anathema.lib.gui.widgets.IChangeableJComboBox;
import net.sf.anathema.lib.util.Identified;
import net.sf.anathema.platform.svgtree.presenter.view.CascadeLoadedListener;
import net.sf.anathema.platform.svgtree.presenter.view.ISvgTreeView;
import net.sf.anathema.platform.svgtree.presenter.view.ISvgTreeViewProperties;
import net.sf.anathema.platform.svgtree.view.SvgTreeView;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.TitledBorder;
import java.awt.BorderLayout;
import java.awt.Dimension;

public abstract class AbstractCascadeSelectionView implements ICascadeSelectionView {

  private final JPanel selectionPanel = new JPanel(new GridDialogLayout(4, false));
  private IChangeableJComboBox<Identified> groupComboBox;
  private IChangeableJComboBox<Identified> typeComboBox;
  private final ISvgTreeView charmTreeView;
    
  public AbstractCascadeSelectionView(ISvgTreeViewProperties treeProperties) {
    this.charmTreeView = new SvgTreeView(treeProperties);
  }

  @Override
  public void addCharmTypeSelector(String title, Identified[] types, ListCellRenderer renderer) {
    JPanel panel = new JPanel(new BorderLayout());
    panel.setBorder(new TitledBorder(title));
    typeComboBox = new ChangeableJComboBox<Identified>(types, false);
    typeComboBox.setSelectedObject(null);
    typeComboBox.setRenderer(renderer);
    panel.add(typeComboBox.getComponent(), BorderLayout.CENTER);
    getSelectionComponent().add(panel);
  }

  @Override
  public void fillCharmGroupBox(Identified[] charmGroups) {
    groupComboBox.setObjects(charmGroups);
  }

  @Override
  public void fillCharmTypeBox(Identified[] charmGroups) {
    typeComboBox.setObjects(charmGroups);
  }

  @Override
  public void addCharmTypeSelectionListener(ObjectValueListener<Identified> selectionListener) {
    typeComboBox.addObjectSelectionChangedListener(selectionListener);
  }

  @Override
  public void addCharmGroupSelector(
      String title,
      ListCellRenderer renderer,
      final ICharmGroupChangeListener selectionListener,
      Dimension preferredSize) {
    JPanel panel = new JPanel(new BorderLayout());
    panel.setBorder(new TitledBorder(title));
    groupComboBox = new ChangeableJComboBox<Identified>(null, false);
    groupComboBox.setSelectedObject(null);
    groupComboBox.setRenderer(renderer);
    groupComboBox.setPreferredSize(preferredSize);
    groupComboBox.addObjectSelectionChangedListener(new ObjectValueListener<Identified>() {
      @Override
      public void valueChanged(Identified newValue) {
        selectionListener.valueChanged(groupComboBox.getSelectedObject(), typeComboBox.getSelectedObject());
      }
    });
    panel.add(groupComboBox.getComponent(), BorderLayout.CENTER);
    getSelectionComponent().add(panel);
  }

  @Override
  public void addCharmFilterButton(SmartAction action, String titleText, String buttonText) {
	  JPanel buttonPanel = new JPanel();
	  JButton filterButton = new JButton();
	  filterButton.setAction(action);
	  filterButton.setText(buttonText);
	  buttonPanel.add(filterButton);
	  
	  TitledBorder title;
	  title = BorderFactory.createTitledBorder(titleText);
	  buttonPanel.setBorder(title);
	  
	  getSelectionComponent().add(buttonPanel);
  }
	
	@Override
	public void addCharmCascadeHelp(String helpText) {
		JLabel help = new JLabel(helpText);
		getSelectionComponent().add( help, GridDialogLayoutData.FILL_HORIZONTAL );
		
	}

  protected final JComponent getSelectionComponent() {
    return selectionPanel;
  }

  protected final ISvgTreeView getCharmTreeView() {
    return charmTreeView;
  }

  @Override
  public CharmTreeRenderer getCharmTreeRenderer() {
    return new SvgCharmTreeRenderer(charmTreeView);
  }

  @Override
  public final void addCascadeLoadedListener(CascadeLoadedListener documentListener) {
    charmTreeView.addDocumentLoadedListener(documentListener);
  }

  public final void dispose() {
    charmTreeView.dispose();
  }

  protected void unselect() {
    typeComboBox.setSelectedObject(null);
    groupComboBox.setSelectedObject(null);
  }

  @Override
  public JComponent getCharmComponent() {
    return getCharmTreeView().getComponent();
  }
}