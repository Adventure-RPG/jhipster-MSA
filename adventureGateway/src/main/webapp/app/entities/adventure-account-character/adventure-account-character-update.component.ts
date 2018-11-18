import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IAdventureAccountCharacter } from 'app/shared/model/adventure-account-character.model';
import { AdventureAccountCharacterService } from './adventure-account-character.service';
import { IAdventureInventoryChar } from 'app/shared/model/adventure-inventory-char.model';
import { AdventureInventoryCharService } from 'app/entities/adventure-inventory-char';
import { IAdventureCharacteristic } from 'app/shared/model/adventure-characteristic.model';
import { AdventureCharacteristicService } from 'app/entities/adventure-characteristic';
import { IAdventureRace } from 'app/shared/model/adventure-race.model';
import { AdventureRaceService } from 'app/entities/adventure-race';
import { IAdventureSkill } from 'app/shared/model/adventure-skill.model';
import { AdventureSkillService } from 'app/entities/adventure-skill';

@Component({
    selector: 'jhi-adventure-account-character-update',
    templateUrl: './adventure-account-character-update.component.html'
})
export class AdventureAccountCharacterUpdateComponent implements OnInit {
    adventureAccountCharacter: IAdventureAccountCharacter;
    isSaving: boolean;

    adventureinventorychars: IAdventureInventoryChar[];

    adventurecharacteristics: IAdventureCharacteristic[];

    adventureraces: IAdventureRace[];

    adventureskills: IAdventureSkill[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private adventureAccountCharacterService: AdventureAccountCharacterService,
        private adventureInventoryCharService: AdventureInventoryCharService,
        private adventureCharacteristicService: AdventureCharacteristicService,
        private adventureRaceService: AdventureRaceService,
        private adventureSkillService: AdventureSkillService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ adventureAccountCharacter }) => {
            this.adventureAccountCharacter = adventureAccountCharacter;
        });
        this.adventureInventoryCharService.query({ filter: 'adventureaccountcharacter-is-null' }).subscribe(
            (res: HttpResponse<IAdventureInventoryChar[]>) => {
                if (!this.adventureAccountCharacter.adventureInventoryCharId) {
                    this.adventureinventorychars = res.body;
                } else {
                    this.adventureInventoryCharService.find(this.adventureAccountCharacter.adventureInventoryCharId).subscribe(
                        (subRes: HttpResponse<IAdventureInventoryChar>) => {
                            this.adventureinventorychars = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.adventureCharacteristicService.query({ filter: 'adventureaccountcharacter-is-null' }).subscribe(
            (res: HttpResponse<IAdventureCharacteristic[]>) => {
                if (!this.adventureAccountCharacter.adventureCharacteristicId) {
                    this.adventurecharacteristics = res.body;
                } else {
                    this.adventureCharacteristicService.find(this.adventureAccountCharacter.adventureCharacteristicId).subscribe(
                        (subRes: HttpResponse<IAdventureCharacteristic>) => {
                            this.adventurecharacteristics = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.adventureRaceService.query().subscribe(
            (res: HttpResponse<IAdventureRace[]>) => {
                this.adventureraces = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.adventureSkillService.query().subscribe(
            (res: HttpResponse<IAdventureSkill[]>) => {
                this.adventureskills = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.adventureAccountCharacter.id !== undefined) {
            this.subscribeToSaveResponse(this.adventureAccountCharacterService.update(this.adventureAccountCharacter));
        } else {
            this.subscribeToSaveResponse(this.adventureAccountCharacterService.create(this.adventureAccountCharacter));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAdventureAccountCharacter>>) {
        result.subscribe(
            (res: HttpResponse<IAdventureAccountCharacter>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackAdventureInventoryCharById(index: number, item: IAdventureInventoryChar) {
        return item.id;
    }

    trackAdventureCharacteristicById(index: number, item: IAdventureCharacteristic) {
        return item.id;
    }

    trackAdventureRaceById(index: number, item: IAdventureRace) {
        return item.id;
    }

    trackAdventureSkillById(index: number, item: IAdventureSkill) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}
