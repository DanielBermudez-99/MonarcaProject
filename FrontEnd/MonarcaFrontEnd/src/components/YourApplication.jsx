import * as React from "react";
import {Table, TableHeader, TableColumn, TableBody, TableRow, TableCell} from "@nextui-org/react";
import {Dropdown, DropdownTrigger, DropdownMenu, DropdownItem, Button} from "@nextui-org/react";


function YourApplication() {
    return (
        <Table aria-label="Example static collection table">
            <TableHeader>
                <TableColumn>NAME</TableColumn>
                <TableColumn>ROLE</TableColumn>
                <TableColumn>STATUS</TableColumn>
                <TableColumn>ACTIONS</TableColumn>
            </TableHeader>
            <TableBody>
                <TableRow key="1">
                    <TableCell>Tony Reichert</TableCell>
                    <TableCell>CEO</TableCell>
                    <TableCell>Active</TableCell>
                    <TableCell><Dropdown>
                        <DropdownTrigger>
                            <Button
                                variant="bordered"
                            >
                                Open Menu
                            </Button>
                        </DropdownTrigger>
                        <DropdownMenu
                            aria-label="Action event example"
                            onAction={(key) => alert(key)}
                        >
                            <DropdownItem key="new">New file</DropdownItem>
                            <DropdownItem key="copy">Copy link</DropdownItem>
                            <DropdownItem key="edit">Edit file</DropdownItem>
                            <DropdownItem key="delete" className="text-danger" color="danger">
                                Delete file
                            </DropdownItem>
                        </DropdownMenu>
                    </Dropdown></TableCell>
                </TableRow>
                <TableRow key="2">
                    <TableCell>Zoey Lang</TableCell>
                    <TableCell>Technical Lead</TableCell>
                    <TableCell>Paused</TableCell>
                    <TableCell><Dropdown>
                        <DropdownTrigger>
                            <Button
                                variant="bordered"
                            >
                                Open Menu
                            </Button>
                        </DropdownTrigger>
                        <DropdownMenu
                            aria-label="Action event example"
                            onAction={(key) => alert(key)}
                        >
                            <DropdownItem key="new">New file</DropdownItem>
                            <DropdownItem key="copy">Copy link</DropdownItem>
                            <DropdownItem key="edit">Edit file</DropdownItem>
                            <DropdownItem key="delete" className="text-danger" color="danger">
                                Delete file
                            </DropdownItem>
                        </DropdownMenu>
                    </Dropdown></TableCell>
                </TableRow>
                <TableRow key="3">
                    <TableCell>Jane Fisher</TableCell>
                    <TableCell>Senior Developer</TableCell>
                    <TableCell>Active</TableCell>
                    <TableCell><Dropdown>
                        <DropdownTrigger>
                            <Button
                                variant="bordered"
                            >
                                Open Menu
                            </Button>
                        </DropdownTrigger>
                        <DropdownMenu
                            aria-label="Action event example"
                            onAction={(key) => alert(key)}
                        >
                            <DropdownItem key="new">New file</DropdownItem>
                            <DropdownItem key="copy">Copy link</DropdownItem>
                            <DropdownItem key="edit">Edit file</DropdownItem>
                            <DropdownItem key="delete" className="text-danger" color="danger">
                                Delete file
                            </DropdownItem>
                        </DropdownMenu>
                    </Dropdown></TableCell>
                </TableRow>
                <TableRow key="4">
                    <TableCell>William Howard</TableCell>
                    <TableCell>Community Manager</TableCell>
                    <TableCell>Vacation</TableCell>
                    <TableCell><Dropdown>
                        <DropdownTrigger>
                            <Button
                                variant="bordered"
                            >
                                Open Menu
                            </Button>
                        </DropdownTrigger>
                        <DropdownMenu
                            aria-label="Action event example"
                            onAction={(key) => alert(key)}
                        >
                            <DropdownItem key="new">Create</DropdownItem>
                            <DropdownItem key="copy">Edit</DropdownItem>
                            <DropdownItem key="edit">Read</DropdownItem>
                            <DropdownItem key="delete" className="text-danger" color="danger">
                                Delete
                            </DropdownItem>
                        </DropdownMenu>
                    </Dropdown></TableCell>
                </TableRow>
            </TableBody>
        </Table>
    );

}

export default YourApplication