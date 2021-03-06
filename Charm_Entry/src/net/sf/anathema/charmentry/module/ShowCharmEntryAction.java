package net.sf.anathema.charmentry.module;

import net.sf.anathema.character.generic.impl.magic.persistence.ICharmEntryData;
import net.sf.anathema.character.generic.magic.ICharmData;
import net.sf.anathema.charmentry.model.WizardCharmEntryModel;
import net.sf.anathema.charmentry.persistence.CharmEntryPropertiesPersister;
import net.sf.anathema.charmentry.persistence.CharmIO;
import net.sf.anathema.charmentry.presenter.HeaderDataEntryPage;
import net.sf.anathema.charmentry.presenter.model.ICharmEntryModel;
import net.sf.anathema.framework.message.MessageUtilities;
import net.sf.anathema.lib.gui.action.SmartAction;
import net.sf.anathema.lib.gui.dialog.core.IDialogResult;
import net.sf.anathema.lib.gui.dialog.core.ISwingFrameOrDialog;
import net.sf.anathema.lib.gui.dialog.wizard.WizardDialog;
import net.sf.anathema.lib.gui.swing.GuiUtilities;
import net.sf.anathema.lib.gui.wizard.AnathemaWizardDialog;
import net.sf.anathema.lib.message.Message;
import net.sf.anathema.lib.resources.IResources;

import javax.swing.Action;
import java.awt.Component;
import java.awt.Cursor;

public class ShowCharmEntryAction extends SmartAction {

  private static final long serialVersionUID = 224753938683474781L;
  private final IResources resources;

  public static Action createMenuAction(IResources resources) {
    return new ShowCharmEntryAction(resources.getString("CharmEntry.Show.Name"), resources); //$NON-NLS-1$
  }

  private ShowCharmEntryAction(String string, IResources resources) {
    this.resources = resources;
    setName(string);
  }

  @Override
  protected void execute(Component parentComponent) {
    parentComponent.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    try {
      ICharmEntryModel model = new WizardCharmEntryModel(null); // need to figure this one out
      ICharmEntryViewFactory viewFactory = new CharmEntryViewFactory(resources);
      HeaderDataEntryPage startPage = new HeaderDataEntryPage(resources, model, viewFactory);
      WizardDialog dialog = new AnathemaWizardDialog(parentComponent, startPage);
      ISwingFrameOrDialog configuredDialog = dialog.getConfiguredDialog();
      configuredDialog.setResizable(false);
      GuiUtilities.centerToParent(configuredDialog.getWindow());
      IDialogResult result = dialog.show();
      if (result.isCanceled()) {
        return;
      }
      ICharmEntryData entryData = model.getCharmData();
      new CharmIO().writeCharmInternal(entryData);
      ICharmData coreData = entryData.getCoreData();
      CharmEntryPropertiesPersister charmEntryPropertiesPersister = new CharmEntryPropertiesPersister();
      charmEntryPropertiesPersister.writeCharmNameProperty(
          coreData.getCharacterType(), coreData.getId(),
          entryData.getName());
      charmEntryPropertiesPersister.writeCharmPageProperty(
          coreData.getCharacterType(),
          coreData.getId(),
          coreData.getPrimarySource(),
          entryData.getPage());
      charmEntryPropertiesPersister.writeDurationProperty(resources, coreData.getDuration());
    }
    catch (Exception e) {
      Message message = new Message("Error occurred while entering charm.", e); //$NON-NLS-1$
      MessageUtilities.indicateMessage(ShowCharmEntryAction.class, parentComponent, message);
    }
    finally {
      parentComponent.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }
  }
}