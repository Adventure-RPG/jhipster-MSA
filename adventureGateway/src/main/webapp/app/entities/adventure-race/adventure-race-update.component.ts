import { Component, OnInit, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IAdventureRace } from 'app/shared/model/adventure-race.model';
import { AdventureRaceService } from './adventure-race.service';
import { IAdventureRaceOptions } from 'app/shared/model/adventure-race-options.model';
import { AdventureRaceOptionsService } from 'app/entities/adventure-race-options';
import { IAdventureFraction } from 'app/shared/model/adventure-fraction.model';
import { AdventureFractionService } from 'app/entities/adventure-fraction';

@Component({
    selector: 'jhi-adventure-race-update',
    templateUrl: './adventure-race-update.component.html'
})
export class AdventureRaceUpdateComponent implements OnInit {
    adventureRace: IAdventureRace;
    isSaving: boolean;

    adventureraceoptions: IAdventureRaceOptions[];

    adventurefractions: IAdventureFraction[];

    constructor(
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private adventureRaceService: AdventureRaceService,
        private adventureRaceOptionsService: AdventureRaceOptionsService,
        private adventureFractionService: AdventureFractionService,
        private elementRef: ElementRef,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ adventureRace }) => {
            this.adventureRace = adventureRace;
        });
        this.adventureRaceOptionsService.query().subscribe(
            (res: HttpResponse<IAdventureRaceOptions[]>) => {
                this.adventureraceoptions = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.adventureFractionService.query().subscribe(
            (res: HttpResponse<IAdventureFraction[]>) => {
                this.adventurefractions = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.adventureRace, this.elementRef, field, fieldContentType, idInput);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.adventureRace.id !== undefined) {
            this.subscribeToSaveResponse(this.adventureRaceService.update(this.adventureRace));
        } else {
            this.subscribeToSaveResponse(this.adventureRaceService.create(this.adventureRace));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAdventureRace>>) {
        result.subscribe((res: HttpResponse<IAdventureRace>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackAdventureRaceOptionsById(index: number, item: IAdventureRaceOptions) {
        return item.id;
    }

    trackAdventureFractionById(index: number, item: IAdventureFraction) {
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
