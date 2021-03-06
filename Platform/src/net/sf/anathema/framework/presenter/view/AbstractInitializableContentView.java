package net.sf.anathema.framework.presenter.view;

import javax.swing.JComponent;
import javax.swing.JPanel;

public abstract class AbstractInitializableContentView<P> implements IInitializableContentView<P> {

  private final JPanel content = new JPanel();

  @Override
  public final void initGui(P properties) {
    createContent(content, properties);
  }

  protected abstract void createContent(JPanel panel, P properties);

  @Override
  public final JComponent getComponent() {
    return content;
  }
}