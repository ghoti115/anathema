package net.sf.anathema.charmentry.model.data;

import net.sf.anathema.character.generic.health.HealthType;

public class ConfigurableHealthCost extends ConfigurableCost implements IConfigurableHealthCost {

  private HealthType type = HealthType.Lethal;

  @Override
  public HealthType getType() {
    return type;
  }

  @Override
  public void setType(HealthType type) {
    this.type = type;
  }
}