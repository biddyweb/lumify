package io.lumify.web.importExportWorkspaces;

import com.altamiracorp.miniweb.Handler;
import com.altamiracorp.miniweb.handlers.StaticResourceHandler;
import io.lumify.web.LumifyCsrfHandler;
import io.lumify.web.WebApp;
import io.lumify.web.WebAppPlugin;
import io.lumify.web.privilegeFilters.AdminPrivilegeFilter;

import javax.servlet.ServletContext;

public class ImportExportWorkspaceWebAppPlugin implements WebAppPlugin {
    @Override
    public void init(WebApp app, ServletContext servletContext, Handler authenticationHandler) {
        Class<? extends Handler> authenticationHandlerClass = authenticationHandler.getClass();
        Class<? extends Handler> csrfHandlerClass = LumifyCsrfHandler.class;

        app.get("/jsc/io/lumify/web/importExportWorkspaces/import.hbs",
                new StaticResourceHandler(getClass(), "/io/lumify/web/importExportWorkspaces/import.hbs", "text/html"));
        app.registerJavaScript("/io/lumify/web/importExportWorkspaces/import-plugin.js");
        app.registerResourceBundle("/io/lumify/web/importExportWorkspaces/messages.properties");

        // TODO: remove legacy when conversion complete
        app.get("/admin/workspaceImport.html", authenticationHandler, new StaticResourceHandler(getClass(), "/workspaceImport.html", "text/html"));
        app.get("/admin/workspaceExport.html", authenticationHandler, new StaticResourceHandler(getClass(), "/workspaceExport.html", "text/html"));
        app.get("/admin/workspace/export", authenticationHandlerClass, csrfHandlerClass, AdminPrivilegeFilter.class, Export.class);
        app.post("/admin/workspace/import", authenticationHandlerClass, csrfHandlerClass, AdminPrivilegeFilter.class, Import.class);
    }
}
