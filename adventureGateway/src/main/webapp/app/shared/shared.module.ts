import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { AdventureGatewaySharedLibsModule, AdventureGatewaySharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [AdventureGatewaySharedLibsModule, AdventureGatewaySharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [AdventureGatewaySharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AdventureGatewaySharedModule {
  static forRoot() {
    return {
      ngModule: AdventureGatewaySharedModule
    };
  }
}
