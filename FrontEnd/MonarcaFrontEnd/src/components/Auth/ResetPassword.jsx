import React, { useState, useEffect } from "react";
import axios from 'axios';
import { Button, Input } from "@nextui-org/react";
import { useHistory, useLocation } from "react-router-dom";

function ResetPassword() {
    const [email, setEmail] = useState('');
    const [token, setToken] = useState('');
    const [newPassword, setNewPassword] = useState('');
    const history = useHistory();
    const location = useLocation();

    useEffect(() => {
        const params = new URLSearchParams(location.search);
        const tokenParam = params.get('token');
        if (tokenParam) {
            setToken(tokenParam);
        }
    }, [location]);

    const requestPasswordReset = (e) => {
        e.preventDefault();
        axios.post('http://localhost:8080/password-reset/password-reset', { email })
            .then(response => {
                console.log(response);
                history.push('/reset-password');
            })
            .catch(error => {
                console.error(error);
            });
    }

    const resetPassword = (e) => {
        e.preventDefault();
        axios.put('http://localhost:8080/password-reset/reset-password', { token, password: newPassword })
            .then(response => {
                console.log(response);
                history.push('/login');
            })
            .catch(error => {
                console.error(error);
            });
    }

    return (
        <div>
            <Input placeholder="Email" value={email} onChange={e => setEmail(e.target.value)} />
            <Button auto onClick={requestPasswordReset}>Request Password Reset</Button>
            <Input placeholder="Token" value={token} onChange={e => setToken(e.target.value)} />
            <Input placeholder="New Password" value={newPassword} onChange={e => setNewPassword(e.target.value)} />
            <Button auto onClick={resetPassword}>Reset Password</Button>
        </div>
    );
}

export default ResetPassword;