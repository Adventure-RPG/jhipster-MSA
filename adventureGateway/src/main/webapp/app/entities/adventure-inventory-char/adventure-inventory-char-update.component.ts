import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IAdventureInventoryChar } from 'app/shared/model/adventure-inventory-char.model';
import { AdventureInventoryCharService } from './adventure-inventory-char.service';
import { IAdventureItem } from 'app/shared/model/adventure-item.model';
import { AdventureItemService } from 'app/entities/adventure-item';

@Component({
    selector: 'jhi-adventure-inventory-char-update',
    templateUrl: './adventure-inventory-char-update.component.html'
})
export class AdventureInventoryCharUpdateComponent implements OnInit {
    adventureInventoryChar: IAdventureInventoryChar;
    isSaving: boolean;

    adventureitems: IAdventureItem[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private adventureInventoryCharService: AdventureInventoryCharService,
        private adventureItemService: AdventureItemService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ adventureInventoryChar }) => {
            this.adventureInventoryChar = adventureInventoryChar;
        });
        this.adventureItemService.query().subscribe(
            (res: HttpResponse<IAdventureItem[]>) => {
                this.adventureitems = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.adventureInventoryChar.id !== undefined) {
            this.subscribeToSaveResponse(this.adventureInventoryCharService.update(this.adventureInventoryChar));
        } else {
            this.subscribeToSaveResponse(this.adventureInventoryCharService.create(this.adventureInventoryChar));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAdventureInventoryChar>>) {
        result.subscribe(
            (res: HttpResponse<IAdventureInventoryChar>) => this.onSaveSuccess(),
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

    trackAdventureItemById(index: number, item: IAdventureItem) {
        return item.id;
    }
}
