import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'adventure-account-character',
        loadChildren: './adventure-account-character/adventure-account-character.module#AdventureGatewayAdventureAccountCharacterModule'
      },
      {
        path: 'adventure-race',
        loadChildren: './adventure-race/adventure-race.module#AdventureGatewayAdventureRaceModule'
      },
      {
        path: 'adventure-fraction',
        loadChildren: './adventure-fraction/adventure-fraction.module#AdventureGatewayAdventureFractionModule'
      },
      {
        path: 'adventure-race-options',
        loadChildren: './adventure-race-options/adventure-race-options.module#AdventureGatewayAdventureRaceOptionsModule'
      },
      {
        path: 'adventure-model',
        loadChildren: './adventure-model/adventure-model.module#AdventureGatewayAdventureModelModule'
      },
      {
        path: 'adventure-skill',
        loadChildren: './adventure-skill/adventure-skill.module#AdventureGatewayAdventureSkillModule'
      },
      {
        path: 'adventure-script',
        loadChildren: './adventure-script/adventure-script.module#AdventureGatewayAdventureScriptModule'
      },
      {
        path: 'adventure-model-options',
        loadChildren: './adventure-model-options/adventure-model-options.module#AdventureGatewayAdventureModelOptionsModule'
      },
      {
        path: 'adventure-category-type',
        loadChildren: './adventure-category-type/adventure-category-type.module#AdventureGatewayAdventureCategoryTypeModule'
      },
      {
        path: 'adventure-characteristic',
        loadChildren: './adventure-characteristic/adventure-characteristic.module#AdventureGatewayAdventureCharacteristicModule'
      },
      {
        path: 'adventure-inventory-char',
        loadChildren: './adventure-inventory-char/adventure-inventory-char.module#AdventureGatewayAdventureInventoryCharModule'
      },
      {
        path: 'adventure-item',
        loadChildren: './adventure-item/adventure-item.module#AdventureGatewayAdventureItemModule'
      },
      {
        path: 'adventure-attributes',
        loadChildren: './adventure-attributes/adventure-attributes.module#AdventureGatewayAdventureAttributesModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AdventureGatewayEntityModule {}
