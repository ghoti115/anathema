package net.sf.anathema.framework.presenter.view;

import net.disy.commons.swing.layout.grid.GridDialogLayout;
import net.disy.commons.swing.layout.grid.GridDialogLayoutData;
import net.sf.anathema.framework.value.IIntValueDisplay;
import net.sf.anathema.lib.control.IIntValueChangedListener;
import net.sf.anathema.lib.control.ObjectValueListener;
import net.sf.anathema.lib.exception.NotYetImplementedException;
import net.sf.anathema.lib.gui.selection.ObjectSelectionView;
import net.sf.anathema.lib.lang.ArrayUtilities;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

public class ObjectSelectionIntValueView implements IIntValueDisplay {

  private final ObjectSelectionView<Integer> view;
  private final JPanel content = new JPanel(new GridDialogLayout(2, false));

  public ObjectSelectionIntValueView(String label, ListCellRenderer renderer, int maximum) {
    this.view = new ObjectSelectionView<Integer>(label, renderer, ArrayUtilities.createIntegerArray(maximum));
  }

  public ObjectSelectionIntValueView(String label, ListCellRenderer renderer, int minimum, int maximum) {
    this.view = new ObjectSelectionView<Integer>(label, renderer, ArrayUtilities.createIntegerArray(minimum, maximum));
  }

  @Override
  public void setValue(int newValue) {
    view.setSelectedObject(newValue);
  }

  @Override
  public void addIntValueChangedListener(final IIntValueChangedListener listener) {
    ObjectValueListener<Integer> changeListener = new ObjectValueListener<Integer>() {
      @Override
      public void valueChanged(Integer newValue) {
        listener.valueChanged(newValue);
      }
    };
    view.addObjectSelectionChangedListener(changeListener);
  }

  @Override
  public void removeIntValueChangedListener(IIntValueChangedListener listener) {
    throw new NotYetImplementedException();
  }

  @Override
  public void setMaximum(int maximalValue) {
    throw new NotYetImplementedException();
  }

  public void setEnabled(boolean enabled) {
    view.setEnabled(enabled);
  }

  @Override
  public JComponent getComponent() {
    view.addTo(content, GridDialogLayoutData.FILL_HORIZONTAL);
    return content;
  }
}