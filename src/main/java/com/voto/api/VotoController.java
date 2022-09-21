package com.voto.api;
import java.util.List;
import java.util.UUID;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import com.voto.domain.entity.VotoEntity;

@Path("/api/voto/v1/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "voto", description = "Operations on resources VotoEntity.")
public class VotoController {

    @GET
    @Operation(summary = "Get all voto")
    public List<VotoEntity> get() {
        return VotoEntity.listAll();
    }

    @GET
    @Path("{id}")
    @APIResponse(responseCode = "200")
    @APIResponse(responseCode = "404", description = "resource not found")
    @Operation(summary = "Find resource by ID")
    public VotoEntity getSindle(@PathParam("id") UUID id) {
        return VotoEntity.findByIdOptional(id).orElseThrow(NotFoundException::new);
    }

    @POST
    @Transactional
    @APIResponse(responseCode = "201",
            description = "VotoEntity created",
            content = @Content(schema = @Schema(implementation = VotoEntity.class)))
    @APIResponse(responseCode = "406", description = "Invalid data")
    @APIResponse(responseCode = "409", description = "VotoEntity already exists")
    @Operation(summary = "Create new VotoEntity")
    public Response create(VotoEntity entity) {
        if (VotoEntity.exists(entity)) {
            return Response.status(Status.CONFLICT).build();
        }

        entity.persist();
        return Response.ok(entity).status(Status.CREATED).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    @APIResponse(responseCode = "200",
            description = "VotoEntity update",
            content = @Content(schema = @Schema(implementation = VotoEntity.class)))
    @APIResponse(responseCode = "404", description = "VotoEntity not found")
    @APIResponse(responseCode = "409", description = "VotoEntity already exists")
    @Operation(summary = "Edit VotoEntity by ID")
    public Response update(@PathParam("id") UUID id, VotoEntity newEntity) {
        VotoEntity entity = VotoEntity.findByIdOptional(id).orElseThrow(NotFoundException::new);

        if (VotoEntity.exists(entity, id)) {
            return Response.status(Status.CONFLICT).build();
        }

        entity.title = newEntity.title;
        

        entity.persist();
        return Response.ok(entity).status(Status.OK).build();
    }

    @DELETE
    @Transactional
    @Path("{id}")
    @APIResponse(responseCode = "204", description = "VotoEntity deleted")
    @APIResponse(responseCode = "404", description = "VotoEntity not found")
    @Operation(summary = "Delete VotoEntity by ID")
    public Response delete(@PathParam("id") UUID id) {
        VotoEntity entity = VotoEntity.findByIdOptional(id).orElseThrow(NotFoundException::new);
        entity.delete();
        return Response.status(Status.NO_CONTENT).build();
    }
}
