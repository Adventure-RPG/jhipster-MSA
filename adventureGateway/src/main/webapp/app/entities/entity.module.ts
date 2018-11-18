import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { AdventureGatewayAdventureAccountCharacterModule } from './adventure-account-character/adventure-account-character.module';
import { AdventureGatewayAdventureRaceModule } from './adventure-race/adventure-race.module';
import { AdventureGatewayAdventureFractionModule } from './adventure-fraction/adventure-fraction.module';
import { AdventureGatewayAdventureRaceOptionsModule } from './adventure-race-options/adventure-race-options.module';
import { AdventureGatewayAdventureModelModule } from './adventure-model/adventure-model.module';
import { AdventureGatewayAdventureSkillModule } from './adventure-skill/adventure-skill.module';
import { AdventureGatewayAdventureScriptModule } from './adventure-script/adventure-script.module';
import { AdventureGatewayAdventureModelOptionsModule } from './adventure-model-options/adventure-model-options.module';
import { AdventureGatewayAdventureCategoryTypeModule } from './adventure-category-type/adventure-category-type.module';
import { AdventureGatewayAdventureCharacteristicModule } from './adventure-characteristic/adventure-characteristic.module';
import { AdventureGatewayAdventureInventoryCharModule } from './adventure-inventory-char/adventure-inventory-char.module';
import { AdventureGatewayAdventureItemModule } from './adventure-item/adventure-item.module';
import { AdventureGatewayAdventureAttributesModule } from './adventure-attributes/adventure-attributes.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        AdventureGatewayAdventureAccountCharacterModule,
        AdventureGatewayAdventureRaceModule,
        AdventureGatewayAdventureFractionModule,
        AdventureGatewayAdventureRaceOptionsModule,
        AdventureGatewayAdventureModelModule,
        AdventureGatewayAdventureSkillModule,
        AdventureGatewayAdventureScriptModule,
        AdventureGatewayAdventureModelOptionsModule,
        AdventureGatewayAdventureCategoryTypeModule,
        AdventureGatewayAdventureCharacteristicModule,
        AdventureGatewayAdventureInventoryCharModule,
        AdventureGatewayAdventureItemModule,
        AdventureGatewayAdventureAttributesModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AdventureGatewayEntityModule {}
