package com.microinvest.app;

import dagger.hilt.InstallIn;
import dagger.hilt.codegen.OriginatingElement;
import dagger.hilt.components.SingletonComponent;
import dagger.hilt.internal.GeneratedEntryPoint;

@OriginatingElement(
    topLevelClass = MicroInvestApplication.class
)
@GeneratedEntryPoint
@InstallIn(SingletonComponent.class)
public interface MicroInvestApplication_GeneratedInjector {
  void injectMicroInvestApplication(MicroInvestApplication microInvestApplication);
}
