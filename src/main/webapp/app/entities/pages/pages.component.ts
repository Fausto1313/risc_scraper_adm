import { Component, OnInit, OnDestroy, ViewChild, AfterViewInit } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import {MatTableDataSource, MatSort} from '@angular/material';
import { IPages } from 'app/shared/model/pages.model';
import { Principal } from 'app/core';
import { ITEMS_PER_PAGE } from 'app/shared';
import { PagesService } from './pages.service';

@Component({
    selector: 'jhi-pages',
    templateUrl: './pages.component.html'
})
export class PagesComponent implements OnInit, OnDestroy, AfterViewInit {
    currentAccount: any;
    pages: IPages[];
    error: any;
    success: any;
    eventSubscriber: Subscription;
    routeData: any;
    links: any;
    totalItems: any;
    queryCount: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;
    filter: String;
    filterTitulo: String;
    filterAutor: String;
    
    public displayedColumns = ['Titulo', 'Fecha', 'Sintesis', 'URL' , 'details', 'update', 'delete'];
    
    public dataSource = new MatTableDataSource<IPages>();
    
    @ViewChild(MatSort) sort: MatSort;

    constructor(
        private pagesService: PagesService,
        private parseLinks: JhiParseLinks,
        private jhiAlertService: JhiAlertService,
        private principal: Principal,
        private activatedRoute: ActivatedRoute,
        private dataUtils: JhiDataUtils,
        private router: Router,
        private eventManager: JhiEventManager
    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe(data => {
            this.page = data.pagingParams.page;
            this.previousPage = data.pagingParams.page;
            this.reverse = data.pagingParams.ascending;
            this.predicate = data.pagingParams.predicate;
        });
    }

    loadAll() {
        this.pagesService
            .query({
                page: this.page - 1,
                size: this.itemsPerPage,
                
            })
            .subscribe(
                (res: HttpResponse<IPages[]>) => this.paginatePages(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
            
       this.pagesService
            .query({
                page: this.page - 1,
                size: this.itemsPerPage,
                
            })
          .subscribe(res => {
            this.dataSource.data = res.body as IPages[];
    });
    }

    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }

    transition() {
        this.router.navigate(['/pages'], {
            queryParams: {
                page: this.page,
                size: this.itemsPerPage,
                
            }
        });
        this.loadAll();
    }

    clear() {
        this.page = 0;
        this.router.navigate([
            '/pages',
            {
                page: this.page,
                
            }
        ]);
        this.loadAll();
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });        
    }

    ngOnDestroy() {
        
    }

    trackId(index: number, item: IPages) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    registerChangeInPages() {
        this.eventSubscriber = this.eventManager.subscribe('pagesListModification', response => this.loadAll());
    }
    
    public doFilter = (value: string) => {
    this.dataSource.filter = value.trim().toLocaleLowerCase();
  }

    

    private paginatePages(data: IPages[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        this.queryCount = this.totalItems;
        this.pages = data;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
    
    ngAfterViewInit(): void {
     this.dataSource.sort = this.sort;
    }
}
