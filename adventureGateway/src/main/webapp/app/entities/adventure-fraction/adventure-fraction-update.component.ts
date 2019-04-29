import { Component, OnInit, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IAdventureFraction } from 'app/shared/model/adventure-fraction.model';
import { AdventureFractionService } from './adventure-fraction.service';
import { IAdventureRace } from 'app/shared/model/adventure-race.model';
import { AdventureRaceService } from 'app/entities/adventure-race';

@Component({
    selector: 'jhi-adventure-fraction-update',
    templateUrl: './adventure-fraction-update.component.html'
})
export class AdventureFractionUpdateComponent implements OnInit {
    adventureFraction: IAdventureFraction;
    isSaving: boolean;

    adventureraces: IAdventureRace[];

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected adventureFractionService: AdventureFractionService,
        protected adventureRaceService: AdventureRaceService,
        protected elementRef: ElementRef,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ adventureFraction }) => {
            this.adventureFraction = adventureFraction;
        });
        this.adventureRaceService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IAdventureRace[]>) => mayBeOk.ok),
                map((response: HttpResponse<IAdventureRace[]>) => response.body)
            )
            .subscribe((res: IAdventureRace[]) => (this.adventureraces = res), (res: HttpErrorResponse) => this.onError(res.message));
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
        this.dataUtils.clearInputImage(this.adventureFraction, this.elementRef, field, fieldContentType, idInput);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.adventureFraction.id !== undefined) {
            this.subscribeToSaveResponse(this.adventureFractionService.update(this.adventureFraction));
        } else {
            this.subscribeToSaveResponse(this.adventureFractionService.create(this.adventureFraction));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IAdventureFraction>>) {
        result.subscribe((res: HttpResponse<IAdventureFraction>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackAdventureRaceById(index: number, item: IAdventureRace) {
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
