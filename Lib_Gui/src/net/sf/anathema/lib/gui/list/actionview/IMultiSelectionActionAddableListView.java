package net.sf.anathema.lib.gui.list.actionview;

public interface IMultiSelectionActionAddableListView<T> extends IActionAddableListView<T> {

  int[] getSelectedIndices();
}